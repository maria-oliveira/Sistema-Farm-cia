package com.farmacia.service;

import com.farmacia.dao.ClienteDAO;
import com.farmacia.dao.MedicamentoDAO;
import com.farmacia.dao.VendaDAO;
import com.farmacia.model.Cliente;
import com.farmacia.model.Medicamento;
import com.farmacia.model.Venda;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MenuService {
    private final MedicamentoDAO medicamentoDAO = new MedicamentoDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final VendaDAO vendaDAO = new VendaDAO();

    public void exibirMenu() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== Sistema de Gestão de Farmácia ===");
            System.out.println("1 - Cadastrar Medicamento");
            System.out.println("2 - Listar Medicamentos");
            System.out.println("3 - Cadastrar Cliente");
            System.out.println("4 - Listar Clientes");
            System.out.println("5 - Registrar Venda");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            int op = sc.nextInt();
            sc.nextLine();

            try {
                switch (op) {
                    case 1 -> cadastrarMedicamento(sc);
                    case 2 -> listarMedicamentos();
                    case 3 -> cadastrarCliente(sc);
                    case 4 -> listarClientes();
                    case 5 -> registrarVenda(sc);
                    case 0 -> {
                        System.out.println("Saindo...");
                        return;
                    }
                    default -> System.out.println("Opção inválida.");
                }
            } catch (SQLException e) {
                System.err.println("Erro de banco de dados: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Erro: " + e.getMessage());
            }
        }
    }

    private void cadastrarMedicamento(Scanner sc) throws SQLException {
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Quantidade: ");
        int qtd = sc.nextInt();
        sc.nextLine();
        System.out.print("Validade (YYYY-MM-DD): ");
        LocalDate validade = LocalDate.parse(sc.nextLine());
        System.out.print("Preço: ");
        BigDecimal preco = sc.nextBigDecimal();
        sc.nextLine();

        Medicamento medicamento = new Medicamento(null, nome, qtd, validade, preco);
        medicamentoDAO.criar(medicamento);
        System.out.println("Medicamento cadastrado!");
    }

    private void listarMedicamentos() throws SQLException {
        List<Medicamento> lista = medicamentoDAO.listarTodos();
        lista.forEach(System.out::println);
    }

    private void cadastrarCliente(Scanner sc) throws SQLException {
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("CPF: ");
        String cpf = sc.nextLine();
        System.out.print("Telefone: ");
        String telefone = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();

        Cliente cliente = new Cliente(null, nome, cpf, telefone, email);
        clienteDAO.criar(cliente);
        System.out.println("Cliente cadastrado!");
    }

    private void listarClientes() throws SQLException {
        List<Cliente> lista = clienteDAO.listarTodos();
        lista.forEach(System.out::println);
    }

    private void registrarVenda(Scanner sc) throws SQLException {
        System.out.print("ID Cliente: ");
        Long idCliente = sc.nextLong();
        System.out.print("ID Medicamento: ");
        Long idMed = sc.nextLong();
        System.out.print("Quantidade: ");
        int qtd = sc.nextInt();
        sc.nextLine();

        // Verifica se medicamento existe
        Medicamento medicamento = medicamentoDAO.buscarPorId(idMed);
        if (medicamento == null) {
            System.out.println("Medicamento não encontrado!");
            return;
        }

        // Verifica estoque
        if (medicamento.getQuantidade() < qtd) {
            System.out.println("Estoque insuficiente!");
            return;
        }

        // Verifica se cliente existe
        Cliente cliente = clienteDAO.buscarPorId(idCliente);
        if (cliente == null) {
            System.out.println("Cliente não encontrado!");
            return;
        }

        // Cria a venda
        Venda venda = new Venda(null, cliente);

        // Passa preço como BigDecimal (assumindo que getPreco retorna BigDecimal)
        venda.adicionarItem(medicamento, qtd, medicamento.getPreco());

        // Salva no banco
        vendaDAO.criarVendaComItens(venda);

        System.out.println("Venda registrada com sucesso!");
    }
}



