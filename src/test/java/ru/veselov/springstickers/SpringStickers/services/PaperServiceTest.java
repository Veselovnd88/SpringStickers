package ru.veselov.springstickers.SpringStickers.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.veselov.springstickers.SpringStickers.model.Paper;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PaperServiceTest {
    private PaperService paperService;
    private Paper mockPaper;

    @BeforeEach
    public void setUpBefore(){
        this.mockPaper = mock(Paper.class);
        this.paperService = new PaperService(mockPaper);
    }

    @Test
    public void testInvocation() throws IOException {
        paperService.save();
        verify(mockPaper).saveWeb();
    }

}