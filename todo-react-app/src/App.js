import React, { useState } from "react";
import {
  BrowserRouter as Router,
  Route,
  Routes,
  Navigate,
} from "react-router-dom";
import Login from "./components/Login";
import Register from "./components/Register";
import Dashboard from "./components/Dashboard";
import "./App.css";

function App() {
  const [jwt, setJwt] = useState(localStorage.getItem("token"));
  const [email, setEmail] = useState(localStorage.getItem("email"));
  // console.log(email);
  return (
    <Router>
      <div className="App">
        <Routes>
          <Route
            path="/"
            element={<Navigate to={jwt ? "/dashboard" : "/login"} />}
          />
          <Route
            path="/login"
            element={<Login setJwt={setJwt} setEmail={setEmail} />}
          />
          <Route path="/register" element={<Register />} />
          <Route
            path="/dashboard"
            element={
              jwt ? (
                <Dashboard email={email} jwt={jwt} setJwt={setJwt} setEmail={setEmail} />
              ) : (
                <Navigate to="/login" />
              )
            }
          />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
