package com.pucpr.medxf.domain.socioeconomico.dto;

public class InformacoesSocioeconomicas {

    private String nomeResponsavel;
    private Integer quantidadeMoradores;
    private String renda;
    private String internet;
    private String beneficio;
    private String planoSaude;
    private String moradia;

    public InformacoesSocioeconomicas() {}

    public String getNomeResponsavel() {
        return nomeResponsavel;
    }

    public void setNomeResponsavel(String nomeResponsavel) {
        this.nomeResponsavel = nomeResponsavel;
    }

    public Integer getQuantidadeMoradores() {
        return quantidadeMoradores;
    }

    public void setQuantidadeMoradores(Integer quantidadeMoradores) {
        this.quantidadeMoradores = quantidadeMoradores;
    }

    public String getRenda() {
        return renda;
    }

    public void setRenda(String renda) {
        this.renda = renda;
    }

    public String getInternet() {
        return internet;
    }

    public void setInternet(String internet) {
        this.internet = internet;
    }

    public String getBeneficio() {
        return beneficio;
    }

    public void setBeneficio(String beneficio) {
        this.beneficio = beneficio;
    }

    public String getPlanoSaude() {
        return planoSaude;
    }

    public void setPlanoSaude(String planoSaude) {
        this.planoSaude = planoSaude;
    }

    public String getMoradia() {
        return moradia;
    }

    public void setMoradia(String moradia) {
        this.moradia = moradia;
    }
}