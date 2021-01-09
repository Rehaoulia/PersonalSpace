package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import net.main.Goal;
import net.main.Milestone;

public class GoalTest {


		
		String gname = "goalname";
		
		@Test
		public void testGoalName() throws ClassNotFoundException{

			Goal goal1 = new Goal(gname);
			Goal goal2 = new Goal();
			 String str = goal1.getName();
			assertTrue(str!="" && str!=null);
			assertFalse(str=="" || str == null);
			assertEquals(gname, str);
			
			assertEquals(goal2.getName(), "Name this goal");
			
		}
		
		@Test
		public void testMilestone() throws ClassNotFoundException{
			
			Goal goal = new Goal();
			ArrayList<Milestone> milestones = goal.getProgressList();
			assertTrue(milestones == null);
			
			goal.addProgress(new Milestone("Title", "Content", false));
			assertTrue(goal.getProgressList() != null);
		}
}


