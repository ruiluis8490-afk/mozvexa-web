# Usa uma imagem oficial do Java 17
FROM eclipse-temurin:17-jdk-alpine

# Define o diretório de trabalho
WORKDIR /app

# Copia todos os ficheiros do projeto para dentro do container
COPY . .

# Dá permissão de execução ao Maven Wrapper e compila o projeto
RUN chmod +x mvnw && ./mvnw clean package -DskipTests

# Expõe a porta 8080 (o Render vai intercetar isto)
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "target/*.jar"]