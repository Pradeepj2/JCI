package com.jci.dao.impl_phase2;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.io.Console;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.ModelAndView;

import com.jci.dao_phase2.ContractGenerationDao2;
import com.jci.model.Contractgeneration;
import com.jci.model.PcsoDateModel;

@Repository
public class ContractGenerationDaoImpl2 implements ContractGenerationDao2 {

	@Autowired
	SessionFactory sessionFactory;

	protected Session currentSession() {
		return sessionFactory.getCurrentSession();
	}

	List<Double> pg = new ArrayList<>();
	List<Double> gc = new ArrayList<>();

	@Override
	public ModelAndView pcso_details(List<String> pcsoDates, String gradeComp, String deliveryType) {

		LocalDate obj = LocalDate.now();
		// LocalDate obj = LocalDate.of(2020, 1, 8)

		int currentyear = obj.getYear();
		int nextyear = 0;
		int month = obj.getMonthValue();

		if (month >= 7) {
			nextyear = currentyear + 1;
		} else {
			nextyear = currentyear;
			currentyear -= 1;
		}

		String cropYear = currentyear + "-" + nextyear;

		List<PcsoDateModel> ll = new ArrayList<>();
		List<Object[]> rows = new ArrayList<>();
		StringBuilder querystr = new StringBuilder("SELECT  mill_name, mill_code,");
		pcsoDates.forEach(date -> {
			querystr.append("SUM(CASE WHEN pcso_date =" + date + "THEN Allocated_qty ELSE 0 END) AS " + date + ",");
		});
//		querystr.deleteCharAt(querystr.length() - 1);
		querystr.append(
				"SUM(Allocated_qty) AS Total_Allocation FROM [XMWJCI].[dbo].[jcientryof_pcso] WHERE pcso_date IN (");

		pcsoDates.forEach(date -> {
			querystr.append(date + ",");
		});
		querystr.deleteCharAt(querystr.length() - 1);
		querystr.append(") GROUP BY mill_name, mill_code");

		SQLQuery query = currentSession().createSQLQuery(querystr.toString());
		rows = query.list();

		String sqlforGrades = "select Jute_combination , Proposed_composition from jcigrade_composition where Label_name='"
				+ gradeComp + "'";

		List<Object[]> list = currentSession().createSQLQuery(sqlforGrades).list();

		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		int i = 0;
		for (Object[] rObject : list) {
			gc.add((Double) rObject[1]);
			System.out.println(rObject[0]);
			System.out.println((Double) rObject[1]);
		}
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();

		String sqlQueryToGetHighestPrice = "select top 1 grade1, grade2, grade3, grade4, grade5, grade6 from jcientry_derivative_price where grade1 + grade2 + grade3 + grade4 + grade5 + grade6 = (select Max(grade1+grade2+grade3+grade4+grade5+grade6) as SumGrades from jcientry_derivative_price where state_name='Assam' and crop_year='"
				+ cropYear + "'and delivery_type='" + deliveryType + "')";

		List<Object[]> listOfGrades = currentSession().createSQLQuery(sqlQueryToGetHighestPrice).list();

		System.out.println(listOfGrades.size());

		for (Object[] gradeP : listOfGrades) {
			// pg.add(gradeP.);

			pg.add(((BigDecimal) gradeP[0]).doubleValue());
			pg.add(((BigDecimal) gradeP[1]).doubleValue());
			pg.add(((BigDecimal) gradeP[2]).doubleValue());
			pg.add(((BigDecimal) gradeP[3]).doubleValue());
			pg.add(((BigDecimal) gradeP[4]).doubleValue());
			pg.add(((BigDecimal) gradeP[5]).doubleValue());

			// System.out.println((Double)gradeP[0]);

			System.out.println(((BigDecimal) gradeP[0]).doubleValue());
			System.out.println(((BigDecimal) gradeP[1]).doubleValue());
			System.out.println(((BigDecimal) gradeP[2]).doubleValue());
			System.out.println(((BigDecimal) gradeP[3]).doubleValue());
			System.out.println(((BigDecimal) gradeP[4]).doubleValue());
			System.out.println(((BigDecimal) gradeP[5]).doubleValue());

		}

		Double totalContractedValue = 0.0;

		List<Double> contractedValueList = new ArrayList<Double>();

		for (Object[] row : rows) {
			int size = row.length;
			Double totalAllocatedToMill = (Double) row[size - 1];
			int sizeOfComponents = pg.size();
			Double contractedValueForPerticularMill = 0.0;
			for (int j = 0; j < sizeOfComponents; j++) {
				// System.out.println(gc.get(j)/100 + "<->" + totalAllocatedToMill +"<->"
				// +pg.get(j));
				contractedValueForPerticularMill += (gc.get(j) / 100) * (totalAllocatedToMill * pg.get(j));
			}
			System.out.println("-------------------------------------------");
			System.out.println(contractedValueForPerticularMill);
			System.out.println("-------------------------------------------");
			contractedValueForPerticularMill = Math.round(contractedValueForPerticularMill * 100.0) / 100.0;
			contractedValueList.add(contractedValueForPerticularMill);
			System.out.println("temp : " + contractedValueForPerticularMill);
			totalContractedValue += contractedValueForPerticularMill;
		}
		totalContractedValue = Math.round(totalContractedValue * 100.0) / 100.0;
		System.err.println(totalContractedValue);

		ModelAndView mView = new ModelAndView();
		mView.addObject("List", rows);
		mView.addObject("totelContractedValue", totalContractedValue);
		mView.addObject("contractedValueMillWise", contractedValueList);
		return mView;
	}

	@Override
	public void create(Contractgeneration contract) {
		currentSession().save(contract);
	}

	@Override
	public int highestDerivativePrice(String cropYear, String deliveryType) {

		return 0;
	}

	@Override
	public boolean isValidContractIdn(String contractIdn) {
		String sql = "select count(contract_id) from jcicontract where Contract_identification_no='" + contractIdn
				+ "'";
		int count = (Integer) currentSession().createSQLQuery(sql).uniqueResult();

		return count > 0 ? true : false;
	}

}
