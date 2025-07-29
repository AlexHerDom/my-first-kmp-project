import SwiftUI
import shared // ← Tu framework KMP

/**
 * ViewModel para iOS que usa el código KMP
 * Sigue el patrón ObservableObject de SwiftUI
 */
class IOSUserViewModel: ObservableObject {
    
    // El ViewModel compartido de KMP
    private let sharedViewModel = SharedUserViewModel()
    
    // Estados para SwiftUI
    @Published var users: [User] = []
    @Published var isLoading: Bool = false
    @Published var errorMessage: String? = nil
    @Published var successMessage: String? = nil
    @Published var selectedUser: User? = nil
    
    init() {
        // Observar cambios del ViewModel compartido
        observeSharedViewModel()
    }
    
    /**
     * Observar estados del ViewModel KMP
     */
    private func observeSharedViewModel() {
        // Observar usuarios
        sharedViewModel.users.watch { [weak self] users in
            DispatchQueue.main.async {
                self?.users = users?.compactMap { $0 as? User } ?? []
            }
        }
        
        // Observar UI state
        sharedViewModel.uiState.watch { [weak self] state in
            guard let state = state else { return }
            
            DispatchQueue.main.async {
                self?.isLoading = state.isLoading
                self?.errorMessage = state.error
                self?.successMessage = state.message
            }
        }
        
        // Observar usuario seleccionado
        sharedViewModel.selectedUser.watch { [weak self] user in
            DispatchQueue.main.async {
                self?.selectedUser = user
            }
        }
    }
    
    // MARK: - Actions (llaman a KMP)
    
    func loadUsers() {
        Task {
            await sharedViewModel.loadUsers()
        }
    }
    
    func findUser(id: Int32) {
        Task {
            await sharedViewModel.findUser(id: id)
        }
    }
    
    func createUser(name: String, email: String) {
        Task {
            await sharedViewModel.createUser(name: name, email: email)
        }
    }
    
    func clearMessages() {
        sharedViewModel.clearMessages()
    }
}

/**
 * Vista SwiftUI que usa el ViewModel
 */
struct UserView: View {
    @StateObject private var viewModel = IOSUserViewModel()
    
    var body: some View {
        NavigationView {
            VStack(spacing: 16) {
                // Header
                Text("👥 Gestión de Usuarios")
                    .font(.title2)
                    .fontWeight(.bold)
                
                // Botones de acción
                HStack(spacing: 12) {
                    Button("🔄 Recargar") {
                        viewModel.loadUsers()
                    }
                    .buttonStyle(.borderedProminent)
                    
                    Button("🔍 Buscar ID:1") {
                        viewModel.findUser(id: 1)
                    }
                    .buttonStyle(.bordered)
                }
                
                // Mensajes de estado
                if let message = viewModel.successMessage {
                    Text(message)
                        .foregroundColor(.green)
                        .padding()
                        .background(Color.green.opacity(0.1))
                        .cornerRadius(8)
                }
                
                if let error = viewModel.errorMessage {
                    Text(error)
                        .foregroundColor(.red)
                        .padding()
                        .background(Color.red.opacity(0.1))
                        .cornerRadius(8)
                }
                
                // Loading
                if viewModel.isLoading {
                    ProgressView("Cargando...")
                } else {
                    // Lista de usuarios
                    List(viewModel.users, id: \.id) { user in
                        VStack(alignment: .leading) {
                            Text(user.name)
                                .font(.headline)
                            Text(user.email)
                                .font(.subheadline)
                                .foregroundColor(.secondary)
                            Text("ID: \(user.id)")
                                .font(.caption)
                                .foregroundColor(.secondary)
                        }
                        .onTapGesture {
                            viewModel.findUser(id: user.id)
                        }
                    }
                }
                
                // Usuario seleccionado
                if let selectedUser = viewModel.selectedUser {
                    VStack(alignment: .leading) {
                        Text("👤 Usuario Seleccionado:")
                            .font(.headline)
                        Text("Nombre: \(selectedUser.name)")
                        Text("Email: \(selectedUser.email)")
                        Text("ID: \(selectedUser.id)")
                    }
                    .padding()
                    .background(Color.blue.opacity(0.1))
                    .cornerRadius(8)
                }
                
                Spacer()
            }
            .padding()
            .onAppear {
                viewModel.loadUsers()
            }
        }
    }
}
