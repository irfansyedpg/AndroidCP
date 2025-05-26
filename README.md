# PDMA Madadgar App Backend

## Table of Contents

* [Introduction](#introduction)
* [Features](#features)
* [Tech Stack](#tech-stack)
* [Prerequisites](#prerequisites)
* [Installation](#installation)
* [Configuration](#configuration)
* [Running the Application](#running-the-application)
* [API Endpoints](#api-endpoints)
* [Push Notifications](#push-notifications)
* [Integration](#integration)
* [Contributing](#contributing)
* [License & Citation](#license--citation)

## Introduction

This repository contains the backend source code for the **PDMA Madadgar App**, a mobile application aimed at improving disaster preparedness, response, and risk reduction in Khyber Pakhtunkhwa, Pakistan. It is a collaborative initiative by:

* Mobilise Project at the Centre for Disaster Preparedness and Management, University of Peshawar
* Provincial Disaster Management Authority (PDMA), Khyber Pakhtunkhwa

The backend serves as the central engine of the PDMA Madadgar App. It manages data storage, exposes a RESTful API for the mobile client, and delivers critical real-time alerts and reports to users.

## Features

* **User Management**: Secure registration, authentication, and profile management
* **Incident Reporting**: Submit, view, and track incident reports by citizens
* **Early Warning Alerts**: Receive real-time weather updates and hazard warnings
* **Daily Situation Reports**: Access PDMA’s daily situation summaries
* **Volunteer Coordination**: Register and manage volunteers for disaster response
* **Damage & Needs Assessment**: Synchronize assessment data collected by officials and communities
* **Push Notifications**: Deliver timely alerts and updates to mobile app users
* **Integration Hooks**: Interfaces for external systems or data sources as required

## Tech Stack

* **Backend**: Java (e.g., Spring Boot), .NET (e.g., ASP.NET Core)
* **Frontend / Scripting**: JavaScript (ES6+)
* **Database**: Microsoft SQL Server
* **Messaging & Notifications**: Firebase Cloud Messaging (FCM) or alternative
* **Hosting & Deployment**: Docker, AWS (EC2, RDS, S3)
* **CI/CD**: GitHub Actions / Bitbucket Pipelines

## Prerequisites

* Python 3.8+
* PostgreSQL 12+
* Docker (optional but recommended)
* AWS account with IAM role for S3 and RDS access (for production)

## Installation

1. **Clone the Repository**

   ```bash
   git clone https://github.com/your-org/pdma-madadgar-backend.git
   cd pdma-madadgar-backend
   ```
2. **Create a Virtual Environment**

   ```bash
   python -m venv .venv
   source .venv/bin/activate   # Linux/macOS
   .\.venv\Scripts\activate    # Windows
   ```
3. **Install Dependencies**

   ```bash
   pip install -r requirements.txt
   ```

## Configuration

Create a `.env` file in the project root with the following variables:

```ini
# Server
DJANGO_SECRET_KEY=your_secret_key
DEBUG=True
ALLOWED_HOSTS=localhost,127.0.0.1

# Database
DB_NAME=madadgar_db
DB_USER=db_user
DB_PASSWORD=db_password
DB_HOST=127.0.0.1
DB_PORT=5432

# AWS / S3 (for media and logs)
AWS_ACCESS_KEY_ID=your_aws_key
AWS_SECRET_ACCESS_KEY=your_aws_secret
AWS_STORAGE_BUCKET_NAME=madadgar-backend-media

# Push Notifications (FCM)
FCM_SERVER_KEY=your_fcm_server_key
```

Adjust settings as needed for production environments.

## Running the Application

1. **Apply Migrations**

   ```bash
   python manage.py migrate
   ```
2. **Create a Superuser**

   ```bash
   python manage.py createsuperuser
   ```
3. **Start the Development Server**

   ```bash
   python manage.py runserver
   ```

Visit `http://127.0.0.1:8000/api/` to explore the API.

## API Endpoints

| Endpoint              | Method | Description                           |
| --------------------- | ------ | ------------------------------------- |
| `/api/auth/register/` | POST   | Register a new user                   |
| `/api/auth/login/`    | POST   | User login                            |
| `/api/incidents/`     | GET    | List all incidents                    |
| `/api/incidents/`     | POST   | Submit new incident report            |
| `/api/alerts/`        | GET    | Fetch latest warning alerts           |
| `/api/reports/daily/` | GET    | Retrieve PDMA Daily Situation Reports |
| `/api/volunteers/`    | GET    | List registered volunteers            |
| `/api/volunteers/`    | POST   | Register as volunteer                 |
| `/api/assessments/`   | GET    | List damage & needs assessments       |
| `/api/assessments/`   | POST   | Submit new assessment data            |

For full documentation, see [API Docs](docs/api.md).

## Push Notifications

Push alerts to users via FCM. Example usage in Django:

```python
from fcm_django.models import FCMDevice

def send_alert(title, body):
    devices = FCMDevice.objects.all()
    devices.send_message(title=title, body=body)
```

## Integration

The backend can integrate with external warning systems or databases. Implement custom Django management commands or Celery tasks to fetch and synchronize external data sources.

## Contributing

1. Fork the repository.
2. Create a feature branch (`git checkout -b feature/YourFeature`).
3. Commit your changes (`git commit -m "Add YourFeature"`).
4. Push to branch (`git push origin feature/YourFeature`).
5. Open a Pull Request.

Please follow the existing code style and ensure all tests pass.

## License & Citation

This project is released under the [MIT License](LICENSE).

If you adapt or derive from this code, please cite the original developers in your application’s introduction:

> “This application is based on the PDMA Madadgar App Backend developed by the Mobilise Project at the Centre for Disaster Preparedness and Management, University of Peshawar, and the Provincial Disaster Management Authority, Khyber Pakhtunkhwa.”
