package Reports;

import Branches.SatisfactionReport;
import Logic.DbQuery;

/**
 * This class extends report generation for concert report
 */ 
public class SatisfactionReportGeneration extends ReportGeneration {
	
	public SatisfactionReportGeneration(DbQuery db, int year, int quarter) {
		super(db, year, quarter);
	}

	@Override
	public String getQueryReport() {
		return "SELECT question.question,AVG(answersurvey.answer) as answer " + 
				"						FROM surveyquestion , answersurvey , survey , question " + 
				"						WHERE survey.surId= surveyquestion.surId and survey.subject='Satisfaction' AND surveyquestion.sqId = answersurvey.sqId and answersurvey.brId=? " + 
				"                       AND Year(survey.activatedDate)=? " + 
				"                       AND quarter(survey.activatedDate)=? and question.qId=surveyquestion.qId " + 
				"						GROUP BY surveyquestion.sqId;";
	}

	@Override
	public Object createObject(String[] row) {
		String question = row[0];
		String avg = row[1];
		
		return new SatisfactionReport(question, avg);
	}

	@Override
	public String toString() {
		return "SatisfactionReport";
	}


}
