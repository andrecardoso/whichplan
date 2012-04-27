package org.whichplan.call;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.whichplan.call.Call;
import org.whichplan.call.CallLog;

import junit.framework.TestCase;

public class CallLogTest extends TestCase {

	public void testSumDurationsShouldSumAllCallsDurations() {
		List<Call> calls = new ArrayList<Call>();		
		calls.add(new Call(new Date(), 30, "888888888", "Oi Móvel"));
		calls.add(new Call(new Date(), 30, "888888128", "TIM"));
		CallLog callLog = new CallLog(calls);
		int sum = callLog.sumDurations();
		assertEquals(60, sum);
	}
	
	public void testSumDurationsByOperatorShouldGroupDurations() {
		List<Call> calls = new ArrayList<Call>();		
		calls.add(new Call(new Date(), 25, "888888888", "Oi Móvel"));
		calls.add(new Call(new Date(), 5, "888888888", "Oi Móvel"));
		calls.add(new Call(new Date(), 25, "32123456", "Oi Fixo"));
		calls.add(new Call(new Date(), 30, "888888128", "TIM"));
		calls.add(new Call(new Date(), 33, "888888128", "TIM"));
		calls.add(new Call(new Date(), 10, "888888128", "TIM"));
		CallLog callLog = new CallLog(calls);
		Map<String, Integer> durationsByOperator = callLog.sumDurationsByOperator();
		assertEquals((30+33+10), durationsByOperator.get("TIM").intValue());
		assertEquals((25+5), durationsByOperator.get("Oi Móvel").intValue());
		assertEquals(25, durationsByOperator.get("Oi Fixo").intValue());
	}
}
