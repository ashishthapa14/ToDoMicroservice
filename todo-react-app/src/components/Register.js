import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import './Register.css';
import axios from 'axios';
import { validateName, validateEmail, validatePassword } from './ValidateRegistration';

function Register() {
    const [email, setEmail] = useState('');
    const [name, setName] = useState('');
    const [password, setPassword] = useState('');
    const [errors, setErrors] = useState({});
    const navigate = useNavigate();

    const handleSubmit = (e) => {
        e.preventDefault();

        const validationErrors = {
            name: validateName(name),
            email: validateEmail(email),
            password: validatePassword(password)
        };

        setErrors(validationErrors);

        if (Object.values(validationErrors).every(error => error === '')) {
            const user = {
                name: name,
                email: email,
                password: password
            };

            axios.post('http://localhost:8383/todo-app/register', user)
                .then(response => {
                    console.log('User registered successfully:', response.data);
                    navigate('/login');
                })
                .catch(error => {
                    console.error('There was an error registering the user!', error);
                    if (error.response && error.response.data) {
                        setErrors({ api: error.response.data.message || 'Registration failed' });
                    }
                });
        }
    };

    return (
        <div className="register-container">
            <form onSubmit={handleSubmit} className="register-form" noValidate>
                <h2>Register</h2>
                {errors.api && <p className="error-message">{errors.api}</p>}
                <div className="form-group">
                    <input
                        type="text"
                        id="name"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        placeholder="Name"
                        required
                        autoFocus
                    />
                    {errors.name && <p className="error-message">{errors.name}</p>}
                </div>
                <div className="form-group">
                    <input
                        type="email"
                        id="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        placeholder="Email"
                        required
                    />
                    {errors.email && <p className="error-message">{errors.email}</p>}
                </div>
                <div className="form-group">
                    <input
                        type="password"
                        id="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        placeholder="Password"
                        required
                    />
                    {errors.password && <p className="error-message">{errors.password}</p>}
                </div>
                <button type="submit" className="register-button">Register</button>
                <div className="text-center mt-3">
                    <p>
                        Already have an account? <Link to="/login">Login here</Link>
                    </p>
                </div>
            </form>
        </div>
    );
}

export default Register;
