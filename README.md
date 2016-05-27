# The Paremus Service Fabric Hello examples

This repository contains example applications for the Paremus Service Fabric. All examples 
can be built locally, or release versions are available from https://nexus.paremus.com. Instructions for running these examples on the Paremus Service Fabric may be found at https://docs.paremus.com/display/SF113/Tutorials.

Each branch contains a separate example application from the hello application sequence. 
Each step in the sequence builds upon a previous step to demonstrate features of the
Paremus Service Fabric, and how to develop applications which use them.

All sources in this repository are provided under the Apache License Version 2.0

## The `hello` application sequence

The following examples are targetted for version 1.13.x of the Paremus Service Fabric

The `hello` application sequence provides a simple introduction to OSGi services, and to
building Service Fabric applications. The various stages of the application are available
in the following branches

 * `hello-1.13.x` - The basic `hello` application, consisting of a service API, implementation, and web viewer
 * `hello-config-1.13.x` - builds upon the `hello-1.13.x` branch to add dynamic configuration
 * `hello-remote-1.13.x` - builds upon the `hello-1.13.x` branch to add remote service discovery and invocation
 * `hello-multiple-1.13.x` - builds upon the `hello-remote-1.13.x` branch to add horizontal scale-out of the application
 * `hello-select-1.13.x` - builds upon the `hello-remote-1.13.x` branch to demonstrate dynamic runtime service selection
  
