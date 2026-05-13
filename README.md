# Whitebox Android Version

## EdTech interactive course platform

Long-term goal of this application is to make education more interactive.
The project is inspired by the most popular EdTech solutions such as: Brilliant, Coursera, Udemy... 

## Open-source Tech-Stack

This repository will follow open-source architecture so that everyone can contribute in enhancing.

### The most important architecture decisions so far:
1. **Kotlin** - native development. The goal of the project is to avoid hardware accessibility limitations when the project will scale.
2. **API** integration. The data for application will be stored on dedicated server. Access to this server will be managed via REST API.
3. **Authentication** and **personalization**. Good education system adjust itself for personal needs. This is where authentication is must-have.
4. **Local storage** and **database**. To ensure that personal progress is being saved and managed properly even offline local implementation is needed.

### More Tech
1. Code - Kotlin (Android minSdk = 24,
   targetSdk = 36)
2. Requests - Redux
3. Auth - Firebase
4. LocalDB - SQLite, (potentially Realm for NoSQL)

### More about Application Architecture
1. Main screen (aka. Dashboard)
    - Active courses and progress there.
    - Find new courses option
2. Profile screen (aka. Achievements)
    - Courses amount
    - Activity graph
    - User information
    - Settings
3. Course screen with a list of Modules
    - Course description
    - List of modules in this course (completed/not)
4. Module screen with content (content can be text, image, video, audio as for v0.1.0)
    - Content of the course with next button
5. Quiz screen with options (test-like as for v0.1.0).
    - Text question and option answer (as for v0.1.0)

### Progress
- [x] Initial commit | [commit](https://github.com/yepiiik/android-whitebox/commit/f2f45376c7a15932a18d09995b02646ea6b5d66c)
- [x] Create empty screens | [commit](https://github.com/yepiiik/android-whitebox/commit/d6e7f2de95d7170414d5356a58ea97db1cefa0d0)
- [x] Add navigation bar to navigate between them | [commit](https://github.com/yepiiik/android-whitebox/commit/d6e7f2de95d7170414d5356a58ea97db1cefa0d0)
- [x] Define course class structure | [commit](https://github.com/yepiiik/android-whitebox/commit/677614c238329b569b028d387c42d0021819a91a)
- [x] Define module class structure | [commit](https://github.com/yepiiik/android-whitebox/commit/677614c238329b569b028d387c42d0021819a91a)
- [x] Define quiz class structure | [commit](https://github.com/yepiiik/android-whitebox/commit/677614c238329b569b028d387c42d0021819a91a)
- [x] Define user class structure | [commit](https://github.com/yepiiik/android-whitebox/commit/677614c238329b569b028d387c42d0021819a91a)
- [x] Implement hard-coded course | [commit](https://github.com/yepiiik/android-whitebox/commit/677614c238329b569b028d387c42d0021819a91a)
- [x] Implement hard-coded module | [commit](https://github.com/yepiiik/android-whitebox/commit/677614c238329b569b028d387c42d0021819a91a)
- [x] Implement hard-coded quiz | [commit](https://github.com/yepiiik/android-whitebox/commit/677614c238329b569b028d387c42d0021819a91a)
- [ ] Implement hard-coded home screen 
- [ ] Implement hard-coded profile screen
- [ ] Implement hard-coded explore screen
- [ ] Implement module reference system
- [ ] Develop markdown component
- [ ] Generate realistic mock data
- [ ] Connect local database
- [ ] Connect firebase auth
- ...
- v0.1.0 checkpoint (aka. MVP)