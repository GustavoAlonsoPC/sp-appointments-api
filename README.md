# Medical Appointments API

## Structural definition

| Table         | Field                                             |
|---------------|---------------------------------------------------|
| Tests:        | {“id”, “name”, “description”}                     |
| affiliates:   | {“id”, “name”, “age”, “mail”}                     |
| appointments: | {“id”, “date”, “hour”, “id_test”, “id_affiliate”} |


## Data constraints

| Field        | Type                |
|--------------|---------------------|
| id:          | number              |
| name:        | string              |
| description: | string              |
| mail:        | string              |
| date:        | Date (‘dd/MM/yyyy’) |
| hour:        | Date (‘hh:mm’)      |


## Controllers


| Controller    | Methods                                                                                                                        |
|---------------|--------------------------------------------------------------------------------------------------------------------------------|
| tests:        | getlist <br/>getbyid {id}<br/> post <br/> put <br/> delete {id}                                                                |
| affiliates:   | getlist <br/>  getbyid {id} <br/> post <br/> put <br/> delete {id}                                                             |
| appointments: | getlist <br/>  getbyid {id} <br/> post <br/> put <br/> delete {id} <br/> getbydate {date} <br/> getbyaffiliates {id_affiliate} |


## Solution

For the solution of this project the following was implemented:

![](imgs/APIModel.png "APIStructure")

# Test Coverage:

![img.png](imgs/img.png)