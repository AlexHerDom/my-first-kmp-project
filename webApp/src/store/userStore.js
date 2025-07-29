// userStore.js - Redux store que usa KMP
import { createSlice } from '@reduxjs/toolkit'
import { UserViewModel, UserRepositoryImpl, UserUseCase } from 'shared' // ← KMP compiled to JS

// Instancia del ViewModel compartido
const sharedViewModel = new UserViewModel(new UserUseCase(new UserRepositoryImpl()))

const userSlice = createSlice({
  name: 'users',
  initialState: {
    users: [],
    selectedUser: null,
    isLoading: false,
    message: null,
    error: null
  },
  reducers: {
    setUsers: (state, action) => {
      state.users = action.payload
    },
    setSelectedUser: (state, action) => {
      state.selectedUser = action.payload
    },
    setLoading: (state, action) => {
      state.isLoading = action.payload
    },
    setMessage: (state, action) => {
      state.message = action.payload
    },
    setError: (state, action) => {
      state.error = action.payload
    },
    clearMessages: (state) => {
      state.message = null
      state.error = null
    }
  }
})

export const { setUsers, setSelectedUser, setLoading, setMessage, setError, clearMessages } = userSlice.actions

// Async actions que usan KMP
export const loadUsers = () => async (dispatch) => {
  dispatch(setLoading(true))
  try {
    await sharedViewModel.loadUsers() // ← Llama a KMP
    
    // Observar cambios del ViewModel KMP
    sharedViewModel.users.collect(users => {
      dispatch(setUsers(users))
    })
    
    sharedViewModel.uiState.collect(state => {
      dispatch(setLoading(state.isLoading))
      dispatch(setMessage(state.message))
      dispatch(setError(state.error))
    })
    
  } catch (error) {
    dispatch(setError(error.message))
    dispatch(setLoading(false))
  }
}

export const findUser = (id) => async (dispatch) => {
  try {
    await sharedViewModel.findUser(id) // ← Llama a KMP
    
    sharedViewModel.selectedUser.collect(user => {
      dispatch(setSelectedUser(user))
    })
  } catch (error) {
    dispatch(setError(error.message))
  }
}

export default userSlice.reducer
