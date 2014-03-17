interapp-eventbus
=================
This working model for InterApp EventBus that was explained in Turducken model - http://goo.gl/jJUEMh

Please see the example in test dir

public class ModuleA implements EntryPoint{
....

InterAppEventBus.fireEvent("test_1");

....

}

public class ModuleB implements EntryPoint{

....

InterAppEventBus.addListener(new InterAppEventHandler(){

public void onEvent(JavaScriptObject data){

....

}

public String getType(){
return "test_1";
}

});
}

Note: Module A and B are deployed in same web page 
and both modules should inherit the InterAppEventBus module


And it supported for Chrome, FireFox, Safari and IE9 - above versions

You can download the jar from - http://goo.gl/bWVXv0

