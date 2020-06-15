<template>
<p>LOGGED OUT </p>
</template>
<script>
export default {
    created()
    {
        this.$store.dispatch('destroyToken')
        .then(
        this.$router.push('/')
      )
    }
}
</script>
