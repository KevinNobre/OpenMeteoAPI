package com.example.OpenMeteoAPI.service;

import com.example.OpenMeteoAPI.Repository.CidadeRepository;
import com.example.OpenMeteoAPI.Repository.ClimaRepository;
import com.example.OpenMeteoAPI.model.Cidade;
import com.example.OpenMeteoAPI.model.Clima;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OpenMeteoService {

    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private ClimaRepository climaRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    public static class OpenMeteoDTO {
        private double latitude;
        private double longitude;
        private CurrentWeather current_weather;

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public CurrentWeather getCurrent_weather() {
            return current_weather;
        }

        public void setCurrent_weather(CurrentWeather current_weather) {
            this.current_weather = current_weather;
        }
    }

    public static class CurrentWeather {
        private double temperature;
        private double windspeed;

        public double getTemperature() {
            return temperature;
        }

        public void setTemperature(double temperature) {
            this.temperature = temperature;
        }

        public double getWindspeed() {
            return windspeed;
        }

        public void setWindspeed(double windspeed) {
            this.windspeed = windspeed;
        }
    }

    public Cidade fetchAndSaveWeather(String nomeCidade) {
        Cidade cidade = cidadeRepository.findByNome(nomeCidade);

        if (cidade == null) {
            throw new RuntimeException("A Cidade não foi encontrada.");
        }

        String url = "https://api.open-meteo.com/v1/forecast?latitude=" + cidade.getLatitude() + "&longitude=" + cidade.getLongitude() + "&current_weather=true";

        OpenMeteoDTO dto = restTemplate.getForObject(url, OpenMeteoDTO.class);

        if (dto == null || dto.getCurrent_weather() == null) {
            throw new RuntimeException("Erro ao buscar dados na API");
        }


        Clima clima = new Clima(
                cidade,
                dto.getCurrent_weather().getTemperature(),
                dto.getCurrent_weather().getWindspeed(),
                LocalDateTime.now()
        );

        climaRepository.save(clima);

        cidade.setTemperaturaAtual(dto.getCurrent_weather().getTemperature());
        cidade.setVelocidadeVento(dto.getCurrent_weather().getWindspeed());

        return cidadeRepository.save(cidade);
    }

    public List<Cidade> listarCidades() {
        return cidadeRepository.findAll();
    }

    public Cidade cadastrarCidade(Cidade cidade) {
        return cidadeRepository.save(cidade);
    }
}