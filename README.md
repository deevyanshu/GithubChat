# Git Blog Pipeline

## Live URL
https://magical-kringle-1f1391.netlify.app/

## About the Project
**Git Blog Pipeline** is a Spring Boot-based application designed to integrate GitHub repositories with AI-powered analysis. The project allows users to load code from a GitHub repository, process it using Google Gemini embeddings, and store it in a Pinecone vector database. Once indexed, users can perform RAG (Retrieval-Augmented Generation) based chats to ask specific questions about the codebase.

The application uses a `QuestionAnswerAdvisor` to filter searches based on the specific repository URL, ensuring that the AI provides context-aware answers relevant to the selected project.

## Tech Stack
*   **Framework:** Spring Boot
*   **AI Integration:** Spring AI
*   **LLM/Embeddings:** Google Gemini (`gemini-embedding-001`)
*   **Vector Database:** Pinecone
*   **Language:** Java
*   **Build Tool:** Maven

## Features
*   **Repository Loading:** Fetch and process code from GitHub URLs.
*   **Vector Search:** Store and retrieve code snippets using high-dimensional embeddings (768 dimensions).
*   **Contextual Chat:** Query the AI about specific logic or structures within a loaded repository using metadata filtering.

## Prerequisites
Before running the application, ensure you have:
*   Java 17 or higher installed.
*   Maven installed.
*   A Google AI API Key (for Gemini).
*   A Pinecone API Key and Environment.

## Installation Guide

1.  **Clone the Repository:**
    ```bash
    git clone <repository-url>
    cd git-blog-pipeline
    ```

2.  **Configure Environment Variables:**
    Open `src/main/resources/application.properties` (or set as environment variables) and provide the following configurations:
    ```properties
    spring.ai.google.genai.api-key=${GOOGLE_AI_API_KEY}
    spring.ai.vectorstore.pinecone.api-key=${PINECONE_API_KEY}
    spring.ai.vectorstore.pinecone.environment=${PINECONE_ENV}
    spring.ai.vectorstore.pinecone.index-name=${PINECONE_INDEX}
    ```

3.  **Build the Project:**
    ```bash
    mvn clean install
    ```

4.  **Run the Application:**
    ```bash
    mvn spring-boot:run
    ```

## API Endpoints

### 1. Load Repository
Processes the GitHub repository and prepares it for the vector store.
*   **URL:** `/api/v1/load`
*   **Method:** `POST`
*   **Params:** `q` (GitHub URL)

### 2. Chat with Repository
Ask questions about a specific repository.
*   **URL:** `/api/v1/chat`
*   **Method:** `POST`
*   **Params:** 
    *   `q`: Your question/query.
    *   `w`: The repository URL (used for filtering context).

## Configuration Details
The project utilizes a custom `AiConfig` to initialize:
*   **ChatClient:** For managing AI interactions.
*   **GoogleGenAiTextEmbeddingModel:** Configured with `gemini-embedding-001` and 768 dimensions for optimal vector representation.
