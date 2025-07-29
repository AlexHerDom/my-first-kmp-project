// UserComponent.jsx - React component
import React, { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { loadUsers, findUser, clearMessages } from '../store/userStore'

const UserComponent = () => {
  const dispatch = useDispatch()
  const { users, selectedUser, isLoading, message, error } = useSelector(state => state.users)

  useEffect(() => {
    dispatch(loadUsers()) // ← Carga inicial usando KMP
  }, [dispatch])

  const handleLoadUsers = () => {
    dispatch(loadUsers()) // ← Usa lógica KMP
  }

  const handleFindUser = (id) => {
    dispatch(findUser(id)) // ← Usa lógica KMP
  }

  const handleClearMessages = () => {
    dispatch(clearMessages())
  }

  return (
    <div className="user-component">
      <h2>👥 Gestión de Usuarios</h2>
      
      {/* Botones de acción */}
      <div className="actions">
        <button onClick={handleLoadUsers} disabled={isLoading}>
          🔄 Recargar
        </button>
        <button onClick={() => handleFindUser(1)} disabled={isLoading}>
          🔍 Buscar ID:1
        </button>
      </div>

      {/* Mensajes */}
      {message && (
        <div className="message success">
          {message}
          <button onClick={handleClearMessages}>×</button>
        </div>
      )}

      {error && (
        <div className="message error">
          {error}
          <button onClick={handleClearMessages}>×</button>
        </div>
      )}

      {/* Loading */}
      {isLoading && <div className="loading">Cargando...</div>}

      {/* Lista de usuarios */}
      <div className="user-list">
        {users.map(user => (
          <div 
            key={user.id} 
            className="user-card"
            onClick={() => handleFindUser(user.id)}
          >
            <h3>{user.name}</h3>
            <p>{user.email}</p>
            <small>ID: {user.id}</small>
          </div>
        ))}
      </div>

      {/* Usuario seleccionado */}
      {selectedUser && (
        <div className="selected-user">
          <h3>👤 Usuario Seleccionado:</h3>
          <p>Nombre: {selectedUser.name}</p>
          <p>Email: {selectedUser.email}</p>
          <p>ID: {selectedUser.id}</p>
        </div>
      )}
    </div>
  )
}

export default UserComponent
