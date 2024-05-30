# Build
custom_build(
   # Name of the container image
   ref = 'catalog-service',
   # Command to build the container image
   command = 'gradlew.bat bootBuildImage --imageName %EXPECTED_REF%',
   # Files to watch that trigger a new build
   deps = ['build.gradle', 'src']
)

# Deploy
k8s_yaml(['deployment/catalog-service/deployment.yaml', 'deployment/catalog-service/service.yaml'])

# Manage
k8s_resource('catalog-service', port_forwards=['80'])
