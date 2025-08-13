package com.farmacia.service;

import com.farmacia.dao.ClienteDAO;
import com.farmacia.dao.MedicamentoDAO;
import com.farmacia.dao.VendaDAO;
import com.farmacia.model.Cliente;
import com.farmacia.model.medicamento;
import com.farmacia.model.Venda;

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
                e.printStackTrace();
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
        double preco = sc.nextDouble();
        sc.nextLine();

        medicamentoDAO.salvar(new medicamento(0, nome, qtd, validade, preco));
        System.out.println("Medicamento cadastrado!");
    }

    private void listarMedicamentos() throws SQLException {
        List<medicamento> lista = medicamentoDAO.listar();
        lista.forEach(System.out::println);
    }

    private void cadastrarCliente(Scanner sc) throws SQLException {
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("CPF: ");
        String cpf = sc.nextLine();

        clienteDAO.salvar(new Cliente(0, nome, cpf));
        System.out.println("Cliente cadastrado!");
    }

    private void listarClientes() throws SQLException {
        List<Cliente> lista = clienteDAO.listar();
        lista.forEach(System.out::println);
    }

    private void registrarVenda(Scanner sc) throws SQLException {
        System.out.print("ID Cliente: ");
        int idCliente = sc.nextInt();
        System.out.print("ID Medicamento: ");
        int idMed = sc.nextInt();
        System.out.print("Quantidade: ");
        int qtd = sc.nextInt();
        sc.nextLine();

        vendaDAO.registrarVenda(new Venda(0, idCliente, idMed, qtd, null));
        System.out.println("Venda registrada!");
    }
}
