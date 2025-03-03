import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function Login() {
    const [email, setEmail] = useState('');
    const [parola, setPassword] = useState('');
    const [message, setMessage] = useState('');
    const navigate = useNavigate();

    const containerStyle: React.CSSProperties = {
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        height: '100vh',
        backgroundColor: '#f4f4f9',
        padding: '0 20px', // Added padding for responsiveness
    };

    const formContainerStyle: React.CSSProperties = {
        display: 'block',
        width: '100%',
        maxWidth: '400px',
        padding: '30px',
        margin: '0 auto',
        boxSizing: 'border-box',
        textAlign: 'center',
        border: '1px solid #e0e0e0',
        borderRadius: '12px',
        backgroundColor: '#ffffff',
        boxShadow: '0px 4px 12px rgba(0, 0, 0, 0.1)',
    };

    const inputStyle: React.CSSProperties = {
        width: '100%',
        padding: '12px',
        marginBottom: '15px',
        borderRadius: '8px',
        border: '1px solid #ccc',
        boxSizing: 'border-box',
        fontSize: '16px',
        transition: 'border-color 0.3s',
    };

    const inputFocusStyle: React.CSSProperties = {
        borderColor: '#4CAF50',
    };

    const buttonStyle: React.CSSProperties = {
        width: '100%',
        padding: '12px',
        backgroundColor: '#007bff',
        color: 'white',
        border: 'none',
        borderRadius: '8px',
        cursor: 'pointer',
        fontSize: '16px',
        transition: 'background-color 0.3s',
    };

    const buttonHoverStyle: React.CSSProperties = {
        backgroundColor: '#0056b3',
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        const loginData = { email, parola };

        const requestParams = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(loginData),
        };

        try {
            const response = await fetch('http://localhost:8082/login', requestParams);
            if (response.ok) {
                const data = await response.json();
                setMessage(`Login successful: `);
                localStorage.setItem('token', data.token);
                navigate('/catalogs');
            } else {
                const errorData = await response.json();
                setMessage(`Login failed: ${errorData.error || 'Unknown error'}`);
            }
        } catch (error) {
            if (error instanceof Error) {
                setMessage(`An error occurred: ${error.message}`);
            } else {
                setMessage('An unknown error occurred');
            }
        }
    };

    return (
        <div style={containerStyle}>
            <div style={formContainerStyle}>
                <h2 style={{ color: '#333' }}>Login</h2>
                <form onSubmit={handleSubmit}>
                    <input
                        type="email"
                        placeholder="Email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                        style={inputStyle}
                        onFocus={(e) => (e.target.style.borderColor = '#4CAF50')}
                        onBlur={(e) => (e.target.style.borderColor = '#ccc')}
                    />
                    <input
                        type="password"
                        placeholder="Password"
                        value={parola}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                        style={inputStyle}
                        onFocus={(e) => (e.target.style.borderColor = '#4CAF50')}
                        onBlur={(e) => (e.target.style.borderColor = '#ccc')}
                    />
                    <button
                        type="submit"
                        style={buttonStyle}
                        onMouseEnter={(e) => (e.target.style.backgroundColor = '#0056b3')}
                        onMouseLeave={(e) => (e.target.style.backgroundColor = '#007bff')}
                    >
                        Login
                    </button>
                </form>
                {message && (
                    <p
                        style={{
                            marginTop: '15px',
                            color: message.startsWith('Login successful') ? 'green' : 'red',
                        }}
                    >
                        {message}
                    </p>
                )}
            </div>
        </div>
    );
}

export default Login;
