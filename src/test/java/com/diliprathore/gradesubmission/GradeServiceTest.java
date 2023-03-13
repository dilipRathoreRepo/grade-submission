package com.diliprathore.gradesubmission;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.diliprathore.gradesubmission.pojo.Grade;
import com.diliprathore.gradesubmission.repository.GradeRepository;
import com.diliprathore.gradesubmission.service.GradeService;

@RunWith(MockitoJUnitRunner.class)
public class GradeServiceTest {

    @Mock
    private GradeRepository gradeRepository;

    @InjectMocks
    private GradeService gradeService;

    @Test
    public void getGradesFromRepoTest(){
        when(gradeRepository.getGrades()).thenReturn(Arrays.asList(
            new Grade("Harry", "Charms", "D+"),
            new Grade("Hermoine", "Potions", "C-")
        ));

        List<Grade> result = gradeService.getGrades();

        assertEquals("Harry", result.get(0).getName());
        assertEquals("Potions", result.get(1).getSubject());
    }


    @Test
    public void gradeIndexTest(){
        when(gradeRepository.getGrades()).thenReturn(Arrays.asList(
            new Grade("Harry", "Charms", "D+"),
            new Grade("Hermoine", "Potions", "C-")
        ));

        List<Grade> result = gradeService.getGrades();

        when(gradeRepository.getGrade(0)).thenReturn(result.get(0));
        when(gradeRepository.getGrade(1)).thenReturn(result.get(1));


        int valid = gradeService.getGradeIndex(result.get(0).getId());
        int invalid = gradeService.getGradeIndex("123");

        assertEquals(0, valid);
        assertEquals(Constants.NOT_FOUND, invalid);
    }

    @Test
    public void getGradeByIdTest() {
        when(gradeRepository.getGrades()).thenReturn(Arrays.asList(
            new Grade("Harry", "Charms", "D+"),
            new Grade("Hermoine", "Potions", "C-")
        ));

        List<Grade> result = gradeService.getGrades();

        when(gradeRepository.getGrade(0)).thenReturn(result.get(0));
        when(gradeRepository.getGrade(1)).thenReturn(result.get(1));

        Grade gradeFound = gradeService.getGradeById(result.get(0).getId());
        Grade gradeNotFound = gradeService.getGradeById((new Grade()).getId());

        assertEquals(result.get(0), gradeFound);
        assertEquals(new Grade(), gradeNotFound);
    }

    @Test
    public void submitGradeAddTest(){
        when(gradeRepository.getGrades()).thenReturn(Arrays.asList(
            new Grade("Harry", "Charms", "D+"),
            new Grade("Hermoine", "Potions", "C-")
        ));

        List<Grade> result = gradeService.getGrades();

        when(gradeRepository.getGrade(0)).thenReturn(result.get(0));
        when(gradeRepository.getGrade(1)).thenReturn(result.get(1));

        Grade newGrade = new Grade("Hermione", "Arthimancy", "C+");
        gradeService.submitGrade(newGrade);

        verify(gradeRepository, times(1)).addGrade(newGrade);
    }

    @Test
    public void submitGradeUpdateTest(){
        when(gradeRepository.getGrades()).thenReturn(Arrays.asList(
            new Grade("Harry", "Charms", "D+"),
            new Grade("Hermoine", "Potions", "C-")
        ));

        List<Grade> result = gradeService.getGrades();

        when(gradeRepository.getGrade(0)).thenReturn(result.get(0));
        when(gradeRepository.getGrade(1)).thenReturn(result.get(1));

        Grade existingGrade = result.get(0);
        gradeService.submitGrade(existingGrade);

        verify(gradeRepository, times(1)).updateGrade(existingGrade, 0);
    }
}
