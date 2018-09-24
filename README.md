# fuel-app

This is an API to fuel management, I developed this API using Intellij IDE and these technologies:

    Springboot;
    Gradle;
    Oracle Database;
    H2 (memory database);
    Swagger;
    Lombok;
    Spring rest.

This API allow these operations:

	Insert new consumption(s): http://localhost:8090/fuel-app/api/fuelConsumption/saveFuelConsumptions
	Report by month and year: http://localhost:8090/fuel-app/api/fuelConsumption/consumeByMonthAndYear
	Report by consume grouped by month and year: http://localhost:8090/fuel-app/api/fuelConsumption/consumeGroupedByMonthAndYear
	Report months statistics by year: http://localhost:8090/fuel-app/api/fuelConsumption/monthlyStatisticsByYear
	
Important, on all reporters services if you don't send the year parameter on payload, the api will assume the current year like parameter.
All requests are post operations and need a valid driver id to has access for the operations, currently when you start the application the class DatabaseLoader will insert some data on database, including a driver data (please check the DatabaseLoader class for more details, if it was deployed on production environment you would need to remove this class).
	
You can check the all rest operations and the payloads through Swagger, to do it you need to running the application and access this url "http://localhost:8090/fuel-app/api/swagger-ui.html"

This project have these kind of tests:

    Integration Tests 12 tests.


Note: You can run the tests through Intellij IDE or call direct in Gradle (execute the command "gradlew clean test" in cmd prompt)

Step to running the project:

    Intellij IDE:
        Import the project like Gradlew Project;
        Mark the check box Enable Annotation Processing in Settings->Build->Execution->Deployment->Compiler->Annotation Processor
        Run the major class FuelAppApplication.java.

    Without an IDE:
        Execute the command "gradlew clean build" inside the project in a cmd prompt;
        Execute command "java -jar fuel-app.jar" in "fuel-app\build\libs" directory.

Important Note: This project use a memory database (Spring H2) but also it is configured to use Oracle database, to use it you just need to configure the connection in production profile (application-prod.properties).

Also, this project has a dockerfile, I mean, you can run this API in a docker container, to do it, you need just build the image (docker build -t fuel-app . ) and create the container (docker run -p 8090:8090 -d fuel-app)
To finish it, I need to say that I didn't have time to implement the feature that allow the app to read files. If you have more questions, please feel free to contact me.
