# katydid-service

![](https://github.com/code-sherpas/katydid-service/workflows/Push%20to%20main/badge.svg)

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=code-sherpas_katydid-service&metric=alert_status)](https://sonarcloud.io/dashboard?id=code-sherpas_katydid-service)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=code-sherpas_katydid-service&metric=bugs)](https://sonarcloud.io/dashboard?id=code-sherpas_katydid-service)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=code-sherpas_katydid-service&metric=code_smells)](https://sonarcloud.io/dashboard?id=code-sherpas_katydid-service)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=code-sherpas_katydid-service&metric=coverage)](https://sonarcloud.io/dashboard?id=code-sherpas_katydid-service)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=code-sherpas_katydid-service&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=code-sherpas_katydid-service)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=code-sherpas_katydid-service&metric=ncloc)](https://sonarcloud.io/dashboard?id=code-sherpas_katydid-service)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=code-sherpas_katydid-service&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=code-sherpas_katydid-service)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=code-sherpas_katydid-service&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=code-sherpas_katydid-service)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=code-sherpas_katydid-service&metric=security_rating)](https://sonarcloud.io/dashboard?id=code-sherpas_katydid-service)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=code-sherpas_katydid-service&metric=sqale_index)](https://sonarcloud.io/dashboard?id=code-sherpas_katydid-service)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=code-sherpas_katydid-service&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=code-sherpas_katydid-service)

#Install EB CLI

Install Python:
```
cd $HOME

sudo apt install python3.9
```
Verify that Python is installed correctly: `python3.9 --version`.

Run this: `sudo apt install python3.9-dev`.

Check pip is not already installed: `python3.9 -m pip --version`.

Download pip: `curl https://bootstrap.pypa.io/get-pip.py -o get-pip.py`.

Install pip: `python3.9 get-pip.py`.

In case of `ModuleNotFoundError: No module named 'distutils.util'` error, run `sudo apt-get install python3-distutils` 
and retry `python3.9 get-pip.py`.

Add the executable path, `/path-to-home/.local/bin`, to your PATH variable in `.bashrc` file:

`export PATH=$PATH:/path-to-home/.local/bin`

`source .bashrc`

`echo $PATH`

Verify that pip is installed correctly:

```
which pip3.9

pip3.9 --version
```
Remove the useless file: `rm get-pip.py`

Use pip to install the EB CLI: `pip3.9 install awsebcli --upgrade --user`

Verify that the EB CLI installed correctly: `eb --version`

To upgrade to the latest version, run the installation command again: `pip3.9 install awsebcli --upgrade --user`