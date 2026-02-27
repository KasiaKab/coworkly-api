# ADR 004 — Security Baseline (JWT Flow)

**Status:** Accepted (self-reviewed)  
**Date:** 2025-11-08  
**Updated:** 2025-11-08

## Context
With the first domain modules stable (`resources`, `bookings`, `time_slots`),
I needed to define the initial security foundation for the monolith.  
The goal was to prepare an authentication layer before implementing `/auth/register` and `/auth/login`.

## Decision
- Authentication and user model introduced under `domain.auth.*`.
- `User` entity uses UUID as identifier and `Role` enum (`USER`, `ADMIN`).
- Passwords are hashed using `BCryptPasswordEncoder`.
- Added `UserService` for registration logic.
- JWT configuration and filters tbd.


## Links
- [Design: user-security-flow.mmd](../designs/user-security-flow.mmd)