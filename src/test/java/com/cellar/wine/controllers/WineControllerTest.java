package com.cellar.wine.controllers;

import com.cellar.wine.model.Wine;
import com.cellar.wine.services.WineService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class WineControllerTest {

    @Mock
    WineService wineService;

    @InjectMocks
    WineController wineController;

    Set<Wine> wines;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        wines = new HashSet<>();
        wines.add(Wine.builder().id(1L).build());
        wines.add(Wine.builder().id(2L).build());

        mockMvc = MockMvcBuilders
                .standaloneSetup(wineController)
                .build();
    }

    @Test
    void listWines() throws Exception {
        when(wineService.findAll()).thenReturn(wines);
        mockMvc.perform(get("/wines"))
                .andExpect(status().isOk())
                .andExpect(view().name("wines/index"))
                .andExpect(model().attribute("wines", hasSize(2)));
    }
}