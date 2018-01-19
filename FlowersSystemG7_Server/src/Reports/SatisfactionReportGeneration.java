package Reports;

import java.util.ArrayList;

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
	public String getQueryReport(int branchId) {
		return "SELECT question.question,AVG(answersurvey.answer) as answer " + 
				"						FROM surveyquestion , answersurvey , survey , question " + 
				"						WHERE survey.surId= surveyquestion.surId and survey.subject='Satisfaction' AND surveyquestion.sqId = answersurvey.sqId and answersurvey.brId=? " + 
				"                       AND Year(survey.activatedDate)=? " + 
				"                       AND quarter(survey.activatedDate)=? and question.qId=surveyquestion.qId " + 
				"						GROUP BY surveyquestion.sqId;";
	}
	
	/**
	 * Read and get the report for specified branch id
	 * @param branchId Branch id to create for it the report
	 * @return Collection of Satisfaction report
	 * @throws Exception Exception when failed on reading the report
	 */
	public ArrayList<Object> getReport(int branchId) throws Exception
	{
		ArrayList<String[]> csvData = getReportInString(branchId);
		
		// convert each column in string array to order report entity
		ArrayList<Object> report = new ArrayList<>();
		
		for (String[] row : csvData)
		{
			String question = row[0];
			String avg = row[1];
			
			SatisfactionReport satisfaction = new SatisfactionReport(question, avg);
			report.add(satisfaction);
		}

		return report;
	}

	@Override
	public String toString() {
		return "SatisfactionReport";
	}

}
