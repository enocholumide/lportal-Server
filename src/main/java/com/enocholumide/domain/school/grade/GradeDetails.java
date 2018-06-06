package com.enocholumide.domain.school.grade;

import com.enocholumide.domain.shared.enumerated.GradeLevel;
import lombok.Data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Data
public class GradeDetails {

    private int enrolled;
    private HashMap<String, Integer> details = new HashMap<String, Integer>();
    private int maximumCount;
    private GradeLevel averageGrade = GradeLevel.A;


    public GradeDetails(List<Grade> grades) {
        this.enrolled = grades.size();
        this.populateInitialSet();
        this.computeStats(grades);
    }

    private void populateInitialSet(){
        GradeLevel[] levels = GradeLevel.values();
        Arrays.stream(levels).forEach(level -> details.put(String.valueOf(level), 0));
    }
    private void computeStats(List<Grade> grades){

        int sum = 0;

        if(grades.size() > 0) {

            for (Grade grade : grades) {

                if (details.containsKey(String.valueOf(grade.getGradeLevel()))) {

                    details.put(String.valueOf(grade.getGradeLevel()), details.get(String.valueOf(grade.getGradeLevel())) + 1);

                    if (this.maximumCount < details.get(String.valueOf(grade.getGradeLevel()))) {
                        this.setMaximumCount(details.get(String.valueOf(grade.getGradeLevel())));
                    }
                    sum += grade.getScore();
                }
            }

            this.setAverageGrade(getGradeFromScore(sum / grades.size()));

        }

    }

    public static GradeLevel getGradeFromScore(double score) {

        if(score < 59.0){
            return GradeLevel.F;
        }
        else if (score < 69.0 ) {
            return GradeLevel.D;
        }
        else if (score < 79.0 ) {
            return GradeLevel.C;
        }
        else if (score < 89.0 ) {
            return GradeLevel.B;
        }

        return GradeLevel.A;
    }
}