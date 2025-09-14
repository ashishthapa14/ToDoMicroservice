import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "./Dashboard.css";

const Dashboard = ({ email, jwt, setJwt, setEmail }) => {
  const navigate = useNavigate();
  const [todos, setTodos] = useState([]);
  const [statsData, setStatsData] = useState([]);
  const [newTodoDescription, setNewTodoDescription] = useState("");
  const [newTodoPriority, setNewTodoPriority] = useState("low");

  const fetchTodos = async () => {
    try {
      const response = await axios.get(
        "http://localhost:8383/todo-app/showToDos",
        {
          headers: {
            jwt: `${jwt}`,
          },
        }
      );
      
      setTodos(response.data);
    } catch (error) {
      console.error("Error fetching todos:", error);
    }
  };

  const fetchStats = async () => {
    try {
      const response = await axios.post("http://localhost:8484/stat/getStat",null, {
        params: {
          jwt: jwt,
          email: email,
        },
      });
      
      setStatsData(response.data);
    } catch (error) {
      console.error("Error fetching stats:", error);
    }
  };

  useEffect(() => {
    if (jwt) {
      // Only fetch if JWT is available
      fetchTodos();
      fetchStats();
    }
  }, [jwt]); // Re-run effect if JWT changes

  const handleAddNewTodo = async () => {
    const newTodo = {
      description: newTodoDescription,
      priority: newTodoPriority,
      email: email,
    };

    try {
      await axios.post("http://localhost:8383/todo-app/newToDo", newTodo, {
        headers: {
          jwt: `${jwt}`,
        },
      });
      setNewTodoDescription("");
      setNewTodoPriority("low");
      fetchTodos(); // Re-fetch todos after successful addition
    } catch (error) {
      console.error("Error adding new todo:", error);
    }
  };

  const handleDelete = async (id) => {
    try {
      await axios.delete(`http://localhost:8383/todo-app/deleteToDo/${id}`, {
        headers: {
          jwt: `${jwt}`,
        },
      });
      fetchTodos(); // Re-fetch todos after successful deletion
    } catch (error) {
      console.error("Error deleting todo:", error);
    }
  };

  const handleLogout = () => {
    localStorage.removeItem("token"); // Clear the JWT token from localStorage
    localStorage.removeItem("email"); // Assuming email is also stored
    setJwt(null); // Clear JWT state in App.js
    setEmail(null); // Clear email state in App.js
    navigate("/login"); // Redirect to login page
  };

  return (
    <div className="dashboard-container">
      <div className="dashboard-header">
        <h2><b>You're logged in!</b></h2>
        <button className="logout-button" onClick={handleLogout}>
          Logout
        </button>
      </div>
      <div className="cards-container">
        <div className="card">
          <h3>My ToDos</h3>
          {todos.length === 0 ? (
        <p>All tasks are completed!</p>
      ) : (
        <table className="dashboard-table">
          <thead>
            <tr>
              <th>Description</th>
              <th>Priority</th>
              <th>Date</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {todos.map((todo) => (
              <tr key={todo.id}>
                <td>{todo.description}</td>
                <td>{todo.priority}</td>
                <td>{todo.dateTime}</td>
                <td>
                  <button className="btn btn-danger" onClick={() => handleDelete(todo.id)}>Delete</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
          <div className="new-todo-form">
            <select className="new-todo-priority" value={newTodoPriority} onChange={(e) => setNewTodoPriority(e.target.value)}>
              <option value="high">High</option>
              <option value="low">Low</option>
            </select>
            <input className="new-todo-description" type="text" placeholder="Description of ToDo" value={newTodoDescription} onChange={(e) => setNewTodoDescription(e.target.value)} />
            <button className="new-todo-submit" onClick={handleAddNewTodo}>
              New To Do
            </button>
          </div>
        </div>
        <div className="card">
          <h3>Present Statistics</h3>
          <table className="dashboard-table">
            <thead>
              <tr>
                <th>id</th>
                <th>Description</th>
              </tr>
            </thead>
            <tbody>
              {statsData.map((item) => (
                <tr key={item.id}>
                  <td>{item.id}</td>
                  <td dangerouslySetInnerHTML={{ __html: item.description }}></td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
