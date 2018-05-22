package pkgEmpty;

import static org.junit.Assert.*;

import org.junit.Test;

import pkgCore.Retirement;

import org.apache.poi.ss.formula.functions.*;

public class TestFinance {

	@Test
	public void TestPV() {

		int iYearsToWork = 40;
		double dAnnualReturnWorking = 0.07;
		int iYearsRetired = 20;
		double dAnnualReturnRetired = 0.02;
		double dRequiredIncome = 10000;
		double dMonthlySSI = 2642;

		double PV = Retirement.PV(dAnnualReturnRetired / 12, iYearsRetired * 12, dRequiredIncome - dMonthlySSI, 0, false);
		
		System.out.println(PV);

		Retirement r = new Retirement(iYearsToWork, dAnnualReturnWorking, iYearsRetired, dAnnualReturnRetired, dRequiredIncome, dMonthlySSI);
		
		double PV1 = Retirement.PV(dAnnualReturnRetired/12/100, iYearsRetired*12, dRequiredIncome - dMonthlySSI, 0, false);
		double PV2 = r.TotalAmountToSave();
		
		System.out.println(PV1);
		System.out.println(PV2);
		
		assertEquals(1454485.55,Math.abs(PV),0.01);
		assertEquals(1454485.55,Math.abs(PV),0.01);
		
	}

	@Test
	public void TestPMT() {

		int YearsToWork = 40;
		double AnnualReturnWorking = .07;
		int YearsRetired = 20;
		double AnnualReturnRetired = .02;
		double RequiredIncome = 10000;
		double MonthlySSI = 2642;
		
		Retirement r = new Retirement(YearsToWork, AnnualReturnWorking, YearsRetired, AnnualReturnRetired, RequiredIncome, MonthlySSI);
		
		@SuppressWarnings("static-access")
		double PMT1 = r.PMT(AnnualReturnWorking/12, YearsToWork*12, 7358, 0, false);
		double PMT2 = r.MonthlySavings();
		
		System.out.println(PMT1);
		System.out.println(PMT2);
		
		assertEquals(554.13,Math.abs(PMT1),.01);
		assertEquals(554.13,Math.abs(PMT2),.01);
		
	}
}
