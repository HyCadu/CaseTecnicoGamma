package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.AreaDoEnem;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Meta} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MetaDTO implements Serializable {

    private Long id;

    @NotNull(message = "must not be null")
    @Min(value = 0)
    @Max(value = 1000)
    private Integer valor;

    private String descricao;

    @NotNull(message = "must not be null")
    private AreaDoEnem area;

    @NotNull
    private AlunoDTO aluno;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public AreaDoEnem getArea() {
        return area;
    }

    public void setArea(AreaDoEnem area) {
        this.area = area;
    }

    public AlunoDTO getAluno() {
        return aluno;
    }

    public void setAluno(AlunoDTO aluno) {
        this.aluno = aluno;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MetaDTO)) {
            return false;
        }

        MetaDTO metaDTO = (MetaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, metaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MetaDTO{" +
            "id=" + getId() +
            ", valor=" + getValor() +
            ", descricao='" + getDescricao() + "'" +
            ", area='" + getArea() + "'" +
            ", aluno=" + getAluno() +
            "}";
    }
}
