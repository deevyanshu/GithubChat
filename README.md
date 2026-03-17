# LinkedInPost - AI-Powered Telegram Bot

LinkedInPost is a Spring Boot application that integrates a Telegram Bot with Spring AI (Google GenAI) to help users create and manage LinkedIn posts directly from their Telegram chat.

## Features
- **Telegram Integration**: Use a Telegram bot as the interface to interact with the service.
- **AI Content Generation**: Leverages Spring AI and Google GenAI to assist in crafting post content.
- **LinkedIn Integration**: Facilitates the process of posting content and media to LinkedIn.
- **State Management**: Handles pending posts and media uploads using concurrent state management.

## Tech Stack
- **Framework**: [Spring Boot 3.5.11](https://spring.io/projects/spring-boot)
- **Language**: Java 17
- **AI Integration**: [Spring AI](https://spring.io/projects/spring-ai) (Google GenAI Starter)
- **Bot Platform**: [TelegramBots Spring Boot Starter](https://github.com/rubenlagus/TelegramBots) (v6.8.0)
- **HTTP Client**: Apache HttpClient 5 / RestTemplate
- **Build Tool**: Maven
- **Utilities**: Project Lombok

## Prerequisites
Before you begin, ensure you have the following:
- **JDK 17** or higher installed.
- **Maven** installed.
- A **Telegram Bot Token** (obtained from [@BotFather](https://t.me/botfather)).
- A **LinkedIn Access Token** (via LinkedIn Developer Portal).
- A **Google AI API Key** (for Spring AI Google GenAI).

## Installation

1. **Clone the Repository**:
   ```bash
   git clone <repository-url>
   cd LinkedInPost
   ```

2. **Configure Environment Variables**:
   Create an `application.properties` file in `src/main/resources` or set the following environment variables:
   ```properties
   # Telegram Configuration
   telegram.bot.token=${TELEGRAM_BOT_TOKEN}
   telegram.bot.username=${TELEGRAM_BOT_USERNAME}

   # LinkedIn Configuration
   linkedin.access.token=${LINKEDIN_ACCESS_TOKEN}

   # Spring AI Configuration (Google GenAI)
   spring.ai.google.genai.api-key=${GOOGLE_AI_API_KEY}
   ```

3. **Build the Project**:
   ```bash
   mvn clean install
   ```

4. **Run the Application**:
   ```bash
   mvn spring-boot:run
   ```

## Project Structure
- `com.deevyanshu.LinkedInPost`: Main application entry point.
- `com.deevyanshu.LinkedInPost.Configuration`: Contains `AiConfig` for bean definitions (ChatClient, RestTemplate, Telegram API).
- `com.deevyanshu.LinkedInPost.Service`: Contains `SocialMediaBot`, the core logic for handling Telegram updates and communicating with LinkedIn.

## Configuration Details
The project includes a custom `RestTemplate` configuration in `AiConfig.java` that uses `HttpComponentsClientHttpRequestFactory` and an interceptor to ensure specific headers (like `Transfer-Encoding`) are managed correctly for API compatibility.
