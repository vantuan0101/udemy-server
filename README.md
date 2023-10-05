# Udemy Project Java 2023

(Backend repo). Clone of Udemy, an e-learning platform, built using SpringBoot. By default, the
app runs on port 9000.

## Frontend & Live Demo

## Requirements

- Java 11 or higher
- MySQL 8.0

### Environmental Variables

You MUST set these ENV variables on your System or Container before you launch this SpringBoot app. **💡TIP**: During
dev/test, you can pass them via `args`, OR store inside your IDE: e.g. In either Eclipse or IntelliJ IDE, in the top
toolbar, find the **"Run"** menu > **Edit/Run Configuration** > **Environment** > **Environmental Variables**. Add (+)
each key and its value, then click **Apply**.

## Important ⚠

Please examine the files [application.yml](src/main/resources/application.yml) (default),
and [application-prod.yml](src/main/resources/application-prod.yml) (meant for _production_). Replace all the necessary
Spring Application properties with yours. But for sensitive info (like Passwords or API Keys), **DON'T PASTE THEM IN
THERE DIRECTLY**❌ . It's safer to store them as Environmental Variables instead (see section above), then either
declare them as `property.name = ${ENV_KEY_NAME}`,

## Databases Used

### MySQL 8.0.x

## Deploying your App 🚀

This App can be easily deployed within few minutes, straight from GitHub to your Cloud PaaS of choice.The following may **require** a Dockerfile: _Dokku,
Railway, Render.com, Fly.io_. Please note, you may also need a **separate** MySQL & Redis instance!

