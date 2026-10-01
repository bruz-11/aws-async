import { useState } from 'react';

function App() {
  const [level, setLevel] = useState('INFO');
  const [message, setMessage] = useState('');
  const [status, setStatus] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!message.trim()) return;

    try {
      const response = await fetch('http://localhost:8080/api/logs', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ level, message }),
      });

      if (response.ok) {
        const text = await response.text();
        setStatus(` Éxito: ${text}`);
        setMessage('');
      } else {
        setStatus(' Error al enviar el log');
      }
    } catch (err) {
      setStatus(` Error de conexión con el backend: ${err.message}`);
    }
  };

  return (
    <div style={{ padding: '2rem', fontFamily: 'Arial, sans-serif', maxWidth: '500px' }}>
      <h2>Sistema de Logging - RabbitMQ</h2>
      <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
        <div>
          <label><strong>Nivel de Severidad:</strong></label>
          <select 
            value={level} 
            onChange={(e) => setLevel(e.target.value)}
            style={{ width: '100%', padding: '0.5rem', marginTop: '0.25rem' }}
          >
            <option value="INFO">INFO</option>
            <option value="WARNING">WARNING</option>
            <option value="ERROR">ERROR</option>
          </select>
        </div>

        <div>
          <label><strong>Mensaje del Log:</strong></label>
          <textarea 
            rows="4" 
            value={message} 
            onChange={(e) => setMessage(e.target.value)} 
            placeholder="Escribe el detalle del evento..."
            style={{ width: '100%', padding: '0.5rem', marginTop: '0.25rem' }}
          />
        </div>

        <button type="submit" style={{ padding: '0.75rem', cursor: 'pointer', fontWeight: 'bold' }}>
          Enviar Log
        </button>
      </form>

      {status && <p style={{ marginTop: '1rem', fontStyle: 'italic' }}>{status}</p>}
    </div>
  );
}

export default App;