# Approaches to create a REST API reference

This repository accompanies a talk on *Approaches to create an REST API reference*.

The approaches considered all have one thing in common - they keep a connection between the API reference and the code.
In other words - they give some guarantees about the API reference and the implementation code being in sync.
This is a very important characteristic of an API reference.

The code in this repository is used to illustrate the different approaches.

All these scenarios use the same REST API implemented with [spring-boot](https://spring.io/projects/spring-boot).

The plain API is contained in the [plain](https://github.com/mduesterhoeft/approaches-to-api-doc-talk/tree/plain) branch.

# Slides

The presentation slides reside [here](https://gitpitch.com/mduesterhoeft/approaches-to-api-doc-talk/plain#/)

## Considered Approaches

### Introspection

This approach relies on application code being introspected to get the information about the REST API.

The library used to implement this approach is [SpringFox](https://springfox.github.io/springfox/).

Branch: [spring-fox-documented](https://github.com/mduesterhoeft/approaches-to-api-doc-talk/tree/spring-fox-documented)

### Test-driven

This approach uses a test-driven approach to generate important parts if an API reference.

Two variants are presented here:
- Use [spring-restdocs](https://spring.io/projects/spring-restdocs) and AsciiDoc - see branch [restdocs-documented](https://github.com/mduesterhoeft/approaches-to-api-doc-talk/tree/restdocs-documented)
- Use [restdocs-openapi](https://github.com/ePages-de/restdocs-openapi) on top of `spring-restdocs` - see branch [openapi-documented](https://github.com/mduesterhoeft/approaches-to-api-doc-talk/tree/openapi-documented)

### Design first

In this approach the focus is on the API design. The connection to the code is established using [Atlassians's swagger-request-validator](https://bitbucket.org/atlassian/swagger-request-validator).

Branch: [open-api-request-validator-documenated](https://github.com/mduesterhoeft/approaches-to-api-doc-talk/tree/open-api-request-validator-documenated)
