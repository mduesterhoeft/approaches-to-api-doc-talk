# Approaches to create a REST API reference

---?color=#00A551

### For public APIs there is no way around an API reference

---?color=#0073BF

### API reference and application code need to be in _sync_

---

## Considered Approaches

The different approaches are shown in the contexts of a spring boot application. See [github repository](https://github.com/mduesterhoeft/approaches-to-api-doc-talk).

@ul

- introspection
- test-driven
- design first

@ulend

---?color=#DC1723

## Introspection

_Use code introspection to get information about the API_

---

@snap[north message-box]
<h2>Introspection</h2>
@snapend

@snap[north-west thumb]
<h3>👍</h3>
@snapend

@snap[north-east thumb]
<h3>👎</h3>
@snapend

@snap[west sw fragment]
@ul[](false)
- easy to apply
- API reference UI ships with code
@ulend
@snapend

@snap[east sw fragment]
@ul[](false)
- intrusive
- pollute code with `@`
- model might be != API
@ulend
@snapend

---?color=#0073BF

## Test-driven

_Generate API reference in the code that is testing API endpoints_

---

@snap[north message-box]
<h2>Test-driven</h2>
@snapend

@snap[north-west thumb]
<h3>👍</h3>
@snapend

@snap[north-east thumb]
<h3>👎</h3>
@snapend

@snap[west sw fragment]
@ul[](false)
- good guarantees in terms of accurateness
- combines assertions and documentation
@ulend
@snapend

@snap[east sw fragment]
@ul[](false)
- test code grows a lot
@ulend
@snapend

---?color=#00B369

## Design First

_Design you API first using an **API specification**. Then **verify** that your API complies with the specification **in your test code**._

---

## Design First - Support the actual work-flow

![design](presentation/design.gif)

API design usually happens **before** starting to implement.

So why not **starting** with the [API specification](https://next.stoplight.io/mduesterhoeft/rnd-day/)

---

@snap[north message-box]
<h2>Design first</h2>
@snapend

@snap[north-west thumb]
<h3>👍</h3>
@snapend

@snap[north-east thumb]
<h3>👎</h3>
@snapend

@snap[west sw fragment]
@ul[](false)
- supports the way teams (usually) work
- good tooling support
- easy tech-writer integration
- low impact on code
@ulend
@snapend

@snap[east sw fragment]
@ul[](false)
- sync between design tool and repository
- certainty limited by verification library
@ulend
@snapend

---?color=#FFCD53

## Questions?
