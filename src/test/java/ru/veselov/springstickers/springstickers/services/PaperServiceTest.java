package ru.veselov.springstickers.springstickers.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.veselov.springstickers.springstickers.model.Paper;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PaperServiceTest {
    @Mock
    SaveToFileService saveToFileService;
    @Mock
    private Paper mockPaper;

    @InjectMocks
    PaperService paperService;


    @Test
    public void testInvocation() throws IOException {
        paperService.save();
        verify(mockPaper).saveWeb();
    }

}