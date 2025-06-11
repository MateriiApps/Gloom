package dev.materii.gloom.ui.screen.repo.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Verified
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.stringResource
import dev.materii.gloom.Res
import dev.materii.gloom.gql.fragment.CommitSignatureDetails
import dev.materii.gloom.ui.component.Avatar
import dev.materii.gloom.util.format

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CommitSignatureDialog(
    signature: CommitSignatureDetails,
    onDismiss: () -> Unit
) {
    AlertDialog(
        icon = {
            Icon(Icons.Outlined.Verified, null)
        },
        title = {
            Text(stringResource(Res.strings.title_commit_verified))
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                val verificationReason = if (signature.wasSignedByGitHub) {
                    Res.strings.commit_signature_reason_github
                } else {
                    Res.strings.commit_signature_reason_user
                }

                Text(
                    text = stringResource(verificationReason),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                HorizontalDivider(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                )

                if (!signature.wasSignedByGitHub) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Avatar(
                            url = signature.signer!!.avatarUrl,
                            modifier = Modifier.size(28.dp)
                        )

                        Text(
                            text = signature.signer!!.login,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }

                    Spacer(Modifier)
                }

                SelectionContainer {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        signature.onGpgSignature?.let { gpgSig ->
                            Text(
                                text = stringResource(Res.strings.label_gpg_key_id, gpgSig.keyId.toString()),
                                style = MaterialTheme.typography.bodySmall,
                                fontFamily = FontFamily.Monospace,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        signature.onSshSignature?.let { sshSig ->
                            Text(
                                text = stringResource(Res.strings.label_ssh_fingerprint, sshSig.keyFingerprint.toString()),
                                style = MaterialTheme.typography.bodySmall,
                                fontFamily = FontFamily.Monospace,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        Text(
                            text = stringResource(Res.strings.label_verified_on, signature.verifiedAt!!.format("MMM d, YYYY, hh:mm a")),
                            style = MaterialTheme.typography.bodySmall,
                            fontFamily = FontFamily.Monospace,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(stringResource(Res.strings.dismiss_okay))
            }
        },
        onDismissRequest = onDismiss
    )
}