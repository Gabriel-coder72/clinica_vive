package com.essenciaviva.clinica.service;

import com.essenciaviva.clinica.domain.Cliente;
import com.essenciaviva.clinica.repository.AgendamentoRepository;
import com.essenciaviva.clinica.repository.ClienteRepository;
import com.essenciaviva.clinica.security.CryptoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private CryptoUtils cryptoUtils;

    // Salva o cliente aplicando criptografia AES-256 e HMAC no CPF
    public Cliente salvarCliente(String nome, String cpf, String nascimento,
                                 String telefone, String email, String endereco, String observacoes) throws Exception {

        Cliente cliente = new Cliente();
        cliente.setNome(nome);

        cliente.setCpfHash(cryptoUtils.generateCpfHmac(cpf));
        cliente.setCpfCifrado(cryptoUtils.encrypt(cpf));
        cliente.setNascimentoCifrado(cryptoUtils.encrypt(nascimento));
        cliente.setTelefoneCifrado(cryptoUtils.encrypt(telefone));
        cliente.setEmailCifrado(cryptoUtils.encrypt(email));
        cliente.setEnderecoCifrado(cryptoUtils.encrypt(endereco));

        if (observacoes != null && !observacoes.isBlank()) {
            cliente.setObservacoesCifradas(cryptoUtils.encrypt(observacoes));
        }

        return clienteRepository.save(cliente);
    }

    // Inativação lógica se o cliente possuir histórico concluído
    public void deletarOuInativarCliente(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com ID: " + clienteId));

        boolean temHistoricoConcluido = agendamentoRepository
                .existsByClienteIdAndSituacao(clienteId, com.essenciaviva.clinica.domain.Agendamento.Situacao.CONCLUIDO);

        if (temHistoricoConcluido) {
            cliente.setStatus(Cliente.Status.INATIVO);
            clienteRepository.save(cliente);
        } else {
            clienteRepository.delete(cliente);
        }
    }
}