/**Copyright 2014 samkumar15@gmail.com

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package org.sambath.gwt.client.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sambath.gwt.client.prototype.InterAppEventHandler;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;

/**
 * @author Sam
 * 
 */
public class InterAppEventBus {

	private static Map<String, List<InterAppEventHandler>> handlersByType = new HashMap<String, List<InterAppEventHandler>>();

	public static void fireEvent(String eventType) {
		_fireEvent(eventType, null);
	}

	public static void fireEvent(String eventType, JavaScriptObject data) {
		_fireEvent(eventType, data);
	}

	private static native void _fireEvent(String eventType,
			JavaScriptObject data)/*-{

		var ua = window.navigator.userAgent;
		var msie = ua.indexOf("MSIE ");
		var event = null;
		if (msie > 0) {
			//since IE9 doesn't support constructor initialization
			event = document.createEvent('CustomEvent');

			event.initCustomEvent(eventType, false, false, data);

		} else {
			//DOM Level 4 Api as default
			event = new CustomEvent(eventType, {
				'detail' : data
			});

		}

		//$doc instead document, since doucment refer to iframe document
		// whereas $doc refer to parent document
		$doc.dispatchEvent(event);
	}-*/;

	public static void addListener(InterAppEventHandler handler) {
		String type = handler.getType();
		if (type == null)
			return;
		List<InterAppEventHandler> list = handlersByType.get(type);
		if (list == null) {
			list = new ArrayList<InterAppEventHandler>();
			handlersByType.put(type, list);
		}
		list.add(handler);
		_addListener(type);
	}

	private static native void _addListener(String type) /*-{
		//$doc instead document, since doucment refer to iframe document
		// whereas $doc refer to parent document
		$doc
				.addEventListener(
						type,
						function(e) {
							$entry(@org.sambath.gwt.client.base.InterAppEventBus::pickListener(Ljava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;)(type,e.detail));
						}, false);
	}-*/;

	private static void pickListener(String type, final JavaScriptObject data) {
		final List<InterAppEventHandler> list = handlersByType.get(type);
		if (list != null) {
			Scheduler.get().scheduleDeferred(new ScheduledCommand() {
				@Override
				public void execute() {
					for (InterAppEventHandler handler : list) {
						handler.onEvent(data);
					}
				}
			});
		}
	}

	public static boolean isSupported() {
		return _isSupported();
	}

	private static native boolean _isSupported()/*-{
		return typeof CustomEvent == 'function';
	}-*/;
}