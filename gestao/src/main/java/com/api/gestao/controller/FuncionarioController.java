package com.api.gestao.controller;

import com.api.gestao.repository.FuncionarioRepository;
import com.api.gestao.model.Funcionario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @GetMapping
    public List<Funcionario> getAllFuncionarios() {
        return funcionarioRepository.findAll();
    }

    @PostMapping
    public Funcionario createFuncionario(@RequestBody Funcionario funcionario) {
        return funcionarioRepository.save(funcionario);
    }

    @DeleteMapping("/{nome}")
    public ResponseEntity<Void> deleteFuncionario(@PathVariable String nome) {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        funcionarios.removeIf(funcionario -> funcionario.getNome().equals(nome));
        funcionarioRepository.deleteAll(funcionarios);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/aumentarSalario")
    public List<Funcionario> updateSalario(@RequestParam BigDecimal percentual) {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        funcionarios.forEach(funcionario -> {
            funcionario.aplicarAumento(percentual);
            funcionarioRepository.save(funcionario);
        });
        return funcionarios;
    }

    @GetMapping("/agrupadosPorFuncao")
    public Map<String, List<Funcionario>> getFuncionariosAgrupadosPorFuncao() {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        return funcionarios.stream().collect(Collectors.groupingBy(Funcionario::getFuncao));
    }

    @GetMapping("/aniversariantes")
    public List<Funcionario> getAniversariantes(@RequestParam List<Integer> meses) {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        return funcionarios.stream()
                .filter(funcionario -> meses.contains(funcionario.getDataNascimento().getMonthValue()))
                .collect(Collectors.toList());
    }

    @GetMapping("/maisVelho")
    public Funcionario getFuncionarioMaisVelho() {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        return funcionarios.stream()
                .min((f1, f2) -> f1.getDataNascimento().compareTo(f2.getDataNascimento()))
                .orElse(null);
    }

    @GetMapping("/ordenadosPorNome")
    public List<Funcionario> getFuncionariosOrdenadosPorNome() {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        return funcionarios.stream()
                .sorted((f1, f2) -> f1.getNome().compareToIgnoreCase(f2.getNome()))
                .collect(Collectors.toList());
    }

    @GetMapping("/totalSalarios")
    public BigDecimal getTotalSalarios() {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        return funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @GetMapping("/salariosMinimos")
    public Map<String, BigDecimal> getSalariosMinimos(@RequestParam BigDecimal salarioMinimo) {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        return funcionarios.stream().collect(Collectors.toMap(Funcionario::getNome, f -> f.calcularSalariosMinimos(salarioMinimo)));
    }
}
