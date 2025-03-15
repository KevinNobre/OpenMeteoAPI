package com.example.OpenMeteoAPI.Repository;

import com.example.OpenMeteoAPI.model.Cidade;
import com.example.OpenMeteoAPI.model.Clima;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClimaRepository extends JpaRepository <Clima, Long> {
    List<Clima> findByCidade(Cidade cidade);

}
