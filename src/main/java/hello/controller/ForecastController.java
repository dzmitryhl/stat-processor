package hello.controller;

import hello.dto.request.ForecastRequestDTO;
import hello.model.*;
import hello.service.ForecastService;
import hello.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
public class ForecastController {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    ForecastService forecastService;

    @Autowired
    MatchService matchService;

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/loadData", method = RequestMethod.POST)
    public TotalResult loadData(@RequestBody ForecastRequestDTO forecastRequestDTO) {
        return forecastService.applyStrategy(forecastRequestDTO);
    }
}
