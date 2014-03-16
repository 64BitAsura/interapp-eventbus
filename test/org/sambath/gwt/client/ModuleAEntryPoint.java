/**
 * Copyright 2014 samkumar15@gmail.com

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/
package org.sambath.gwt.client;

import org.sambath.gwt.client.base.InterAppEventBus;
import org.sambath.gwt.client.prototype.InterAppEventHandler;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayInteger;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author Sam
 * 
 */
public class ModuleAEntryPoint implements EntryPoint {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	@Override
	public void onModuleLoad() {

		if (!InterAppEventBus.isSupported()) {
			Window.alert("Sorry, Custom event in this browser.");
		}

		Button button = new Button();
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				JsArrayInteger data = ((JsArrayInteger) JsArrayInteger
						.createArray(1));
				data.push(1);
				data.push(2);
				InterAppEventBus.fireEvent("test1", data);
			}
		});

		InterAppEventBus.addListener(new InterAppEventHandler() {

			@Override
			public void onEvent(JavaScriptObject data) {
				Window.alert(getType()
						+ " event captured in parent module and data is "
						+ data.toString());
			}

			@Override
			public String getType() {
				return "test1";
			}
		});

		button.setText("Test");

		RootPanel.get("module_A").add(button);
	}

}
