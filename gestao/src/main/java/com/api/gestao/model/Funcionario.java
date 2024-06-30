package com.api.gestao.model;

import jakarta.persistence.Entity;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Entity
public class Funcionario extends Pessoa {
    private BigDecimal salario;
    private String funcao;

    public Funcionario() {
    }

    public Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
        super(nome, dataNascimento);
        this.salario = salario;
        this.funcao = funcao;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public void aplicarAumento(BigDecimal percentual) {
        BigDecimal aumento = salario.multiply(percentual);
        this.salario = this.salario.add(aumento);
    }

    public BigDecimal calcularSalariosMinimos(BigDecimal salarioMinimo) {
        return this.salario.divide(salarioMinimo, 2, RoundingMode.HALF_UP);
    }

    @Override
    public String toString() {
        return "Funcionario{" +
                "salario=" + salario +
                ", funcao='" + funcao + '\'' +
                "} " + super.toString();
    }
}
