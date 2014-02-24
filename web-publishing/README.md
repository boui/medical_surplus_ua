# Example application for Play+AngularJS+RequireJS.

## Intro

## Code Organization

The JavaScript modules are organized as follows:

    |- app
    |-- assets
    |--- javascripts    <- contains all the JavaScript/CoffeeScript modules
    |---- app.js        <- app module, wires everything together
    |---- mainDev.js    <- tells RequireJS how to load modules in DEV mode and bootstraps app
    |---- mainProd.js   <- tells RequireJS how to load modules in PROD mode and bootstraps app
    |---- common/       <- a module, in this case
    |----- main.js      <- main file of the module, loads all sub-files in this folder
    |----- filters.js   <- common's filters
    |----- directives/  <- common's directives
    |----- services/    <- common's services
    |---- ...


## Trying It Out

### Dev Mode

* create file 'public/generated/fizrastvor'
* Load dependencies via `play update`
* Run via `play ~run`
* Go to localhost:9000

### Prod Mode

* set output.dir configuration parameter
* create file '<output-dir>/generated/fizrastvor'
* Produce executable via `play stage`
* Run `target/start -Dhttp.port=9000`

