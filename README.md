# Medical Appointments API

## Definición estructural

| Tabla  |  Campos |
|--------|--------|
|Tests: | {“id”, “name”, “description”} |
|affiliates: | {“id”, “name”, “age”, “mail”} |
|appoinments: | {“id”, “date”, “hour”, “id_test”, “id_affiliate”} |


## Contrato de datos

| Campo        | Tipo   |
|--------------|--------|
| id:          | number |
| name:        | string |
| description: | string |
| mail:        | string |
| date:        | Date (‘dd/MM/yyyy’) |
| hour:        | Date (‘hh:mm’) |


## Controllers


| Controller    | Methods                                                                                                                 |
|---------------|-------------------------------------------------------------------------------------------------------------------------|
| tests:        | getlist <br/>getbyid {id}<br/> post <br/> put <br/> delete {id}                                                         |
| affiliates:   | getlist <br/>  getbyid {id} <br/> post <br/> put <br/> delete {id}                                                      |
| appointments: | getlist <br/>  getbyid {id} <br/> post <br/> put <br/> delete {id} <br/> getbydate {date} <br/> getbyaffiliates {id_affiliate} |


