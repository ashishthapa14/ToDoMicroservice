
import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import './Login.css';
import axios from 'axios';

const Login = ({ setJwt, setEmail }) => {
  const [emailInput, setEmailInput] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post(
        'http://localhost:8383/todo-app/login',
        null,
        {
          params: {
            email: emailInput,
            password: password,
          },
        }
      );
  
      const jwt = response.headers['jwt']; // lowercase key
     
  
      if (response.status === 200 && jwt) {
        setJwt(jwt);
        setEmail(emailInput);
        localStorage.setItem("token", jwt); // better than document.cookie
        localStorage.setItem("email", emailInput);
        navigate('/dashboard');
      } else {
        alert('Sign in failed!');
      }
    } catch (error) {
      console.error('Error during login:', error.response?.data || error.message);
      alert('An error occurred during login.');
    }
  };

  return (
    <div className="login-container">
      <form onSubmit={handleLogin} className="login-form">
        <h2>Login</h2>
        <div className="form-group">
          <input
            type="email"
            placeholder="Your Email"
            autoFocus
            value={emailInput}
            onChange={(e) => setEmailInput(e.target.value)}
          />
        </div>
        <div className="form-group">
          <input
            type="password"
            placeholder="Your Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </div>
        <button type="submit" className="login-button">
          Login
        </button>
        <div className="text-center mt-3">
          <p>
            Don't have an account? <Link to="/register">Register here</Link>
          </p>
        </div>
      </form>
    </div>
  );
};

export default Login;
