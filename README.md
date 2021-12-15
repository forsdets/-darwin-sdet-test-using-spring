# darwin-sdet-tests

## Run the API Tests

#### Run the API Tests through Cucumber Runner class

Steps to follow:

- Import gradle Dependencies

- Check the BDD Feature files are available in:

```> src/test/resources/features```

- In IDE Go to **src> test> java> runner> RunCuke**

- Right click and Run by setting the below runtime parameter

The Run time parameter needs to be updated based on which environment we are running.

```bash
-Dservice.uri=https://jsonplaceholder.typicode.com
```

- The 'tags' parameter will give the option to mention the feature or scenario tag to run

#### Run the API Tests through gradle task or Pipeline or standalone Jenkins job

From IDE's terminal or gitbash or  we can use the below parameters to run API scenarios
Syntax:
```bash
./gradle clean cucumber -Dservice.uri=https://jsonplaceholder.typicode.com
```
Example:
```bash
./gradle clean cucumber -Dservice.uri=https://jsonplaceholder.typicode.com
```

Similarly, we can run for other environments by configuring/providing the above 2 parameters in runtime on Pipeline or standalone Jenkins Job.

These tests can be integrated easily with CI/CD Pipeline by creating a new test stage, and it can be executed whenever we are making a change in this API.
