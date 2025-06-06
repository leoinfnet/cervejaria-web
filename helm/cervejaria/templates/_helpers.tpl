{{- define  "cervejaria.readinessProbe"}}
readinessProbe:
    httpGet:
      port: 8080
      path: /login
    periodSeconds: 10
    timeoutSeconds: 2
    initialDelaySeconds: 5
    failureThreshold: 2
{{- end}}
{{- define  "cervejaria.livenessProbe"}}
livenessProbe:
    httpGet:
      port: 8080
      path: /login
    initialDelaySeconds: 10
    periodSeconds: 30
    timeoutSeconds: 2
    failureThreshold: 3
{{- end}}

