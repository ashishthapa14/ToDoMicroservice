
import React, { useState, useEffect } from 'react';
import { Container, Row, Col, Card, Table, Button, Form, InputGroup } from 'react-bootstrap';

const TodoApp = ({ jwt, email, setJwt }) => {
  const [todos, setTodos] = useState([]);
  const [statistics, setStatistics] = useState([]);
  const [newTodoDescription, setNewTodoDescription] = useState('');
  const [newTodoPriority, setNewTodoPriority] = useState('');

  const getCookie = (name) => {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop().split(';').shift();
  };

  const showToDos = async () => {
    try {
      const response = await fetch('http://localhost:8383/showToDos', {
        method: 'POST',
      });
      const data = await response.json();
      setTodos(data.response);
    } catch (error) {
      console.error('Error fetching todos:', error);
    }
  };

  const getStatistics = async () => {
    try {
      const response = await fetch('http://localhost:8384/getStatistics', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: new URLSearchParams({
          jwt: getCookie('jwt'),
          email: email,
        }),
      });
      const data = await response.json();
      setStatistics(data.response);
    } catch (error) {
      console.error('Error fetching statistics:', error);
    }
  };

  const deleteToDo = async (id) => {
    try {
      await fetch(`http://localhost:8383/deleteToDo/${id}`, {
        method: 'POST',
      });
      showToDos();
    } catch (error) {
      console.error('Error deleting todo:', error);
    }
  };

  const handleNewTodo = async () => {
    if (!newTodoPriority) {
      alert('Insert a valid priority value');
      return;
    }
    if (newTodoDescription === '') {
      alert('Insert a valid description value');
      return;
    }

    try {
      await fetch('http://localhost:8383/newToDo', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: new URLSearchParams({
          description: newTodoDescription,
          priority: newTodoPriority,
          fkUser: email,
        }),
      });
      setNewTodoDescription('');
      setNewTodoPriority('');
      showToDos();
    } catch (error) {
      console.error('Error creating new todo:', error);
    }
  };

  const handleSignOut = () => {
    document.cookie = 'jwt=;expires=Thu, 01 Jan 1970 00:00:01 GMT;';
    setJwt(null);
    alert("You're logged out!");
  };

  useEffect(() => {
    showToDos();
    getStatistics();
  }, []);

  const convertDate = (inputFormat) => {
    const pad = (s) => (s < 10 ? '0' + s : s);
    const d = new Date(inputFormat);
    return [pad(d.getDate()), pad(d.getMonth() + 1), d.getFullYear()].join('/');
  };

  return (
    <Container className="mt-5">
      <Row>
        <Col>
          <div className="d-flex justify-content-between align-items-center mb-3">
            <h2>You're logged in!</h2>
            <Button variant="outline-primary" onClick={handleSignOut}>
              Sign out
            </Button>
          </div>
        </Col>
      </Row>
      <Row>
        <Col>
          <Card className="mb-4">
            <Card.Body>
              <Card.Title className="text-center">My ToDos</Card.Title>
              <Table striped bordered hover responsive>
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>DESCRIPTION</th>
                    <th>PRIORITY</th>
                    <th>DATE</th>
                    <th>DELETE</th>
                  </tr>
                </thead>
                <tbody>
                  {todos.map((todo) => (
                    <tr key={todo.id}>
                      <td>{todo.id}</td>
                      <td>{todo.description}</td>
                      <td>{todo.priority}</td>
                      <td>{convertDate(todo.date)}</td>
                      <td>
                        <Button variant="danger" size="sm" onClick={() => deleteToDo(todo.id)}>
                          X
                        </Button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </Table>
              <InputGroup className="mb-3">
                <Form.Select
                  value={newTodoPriority}
                  onChange={(e) => setNewTodoPriority(e.target.value)}
                >
                  <option value="" disabled>
                    Priority
                  </option>
                  <option value="high">high</option>
                  <option value="low">low</option>
                </Form.Select>
                <Form.Control
                  placeholder="Description of ToDo"
                  value={newTodoDescription}
                  onChange={(e) => setNewTodoDescription(e.target.value)}
                />
                <Button variant="success" onClick={handleNewTodo}>
                  New To Do
                </Button>
              </InputGroup>
            </Card.Body>
          </Card>
        </Col>
      </Row>
      <Row>
        <Col>
          <Card>
            <Card.Body>
              <Card.Title className="text-center">Present Statistics</Card.Title>
              <Table striped bordered hover responsive>
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>DESCRIPTION</th>
                    <th>DATE</th>
                    <th>EMAIL</th>
                  </tr>
                </thead>
                <tbody>
                  {statistics.map((stat) => (
                    <tr key={stat.id}>
                      <td>{stat.id}</td>
                      <td>{stat.description}</td>
                      <td>{convertDate(stat.date)}</td>
                      <td>{stat.email}</td>
                    </tr>
                  ))}
                </tbody>
              </Table>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
};

export default TodoApp;
