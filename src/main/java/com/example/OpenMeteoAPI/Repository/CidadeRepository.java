package com.example.OpenMeteoAPI.Repository;

import com.example.OpenMeteoAPI.model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {
    Cidade findByNome(String nome);


}
