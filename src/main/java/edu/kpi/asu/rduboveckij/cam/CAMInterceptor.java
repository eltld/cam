package edu.kpi.asu.rduboveckij.cam;

import java.lang.reflect.Method;

import android.app.Activity;

public class CAMInterceptor {
	private Method onServer;

	public Object invoke(Method invocation)  {
		/*CAMImpl cam = new CAMImpl();
		Activity activity = (Activity) invocation.getArguments()[0];
		StopWatch watch = new StopWatch();
		watch.start(invocation.getMethod().getName());
		Object result;
		if (cam.isOnClient(activity)) {
			result = invocation.proceed();
			cam.saveLogTime(watch.getTotalTimeSeconds());
		} else {
			result = onServer.invoke(null, invocation.getArguments());
		}
		watch.stop();*/
		
		return null;
	}

}
