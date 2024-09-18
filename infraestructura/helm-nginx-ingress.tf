resource "helm_release" "nginx-externo-tf" {
  name = "nginx-externo-tf"

  repository       = "https://kubernetes.github.io/ingress-nginx"
  chart            = "ingress-nginx"
  namespace        = "ingress"
  create_namespace = true
  version          = "4.10.1"

  values = [file("nginx/nginx-ingress.yaml")]

  depends_on = [helm_release.helm-load-balancer-tf]
}


